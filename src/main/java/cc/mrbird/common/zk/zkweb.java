package cc.mrbird.common.zk;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.base64.Base;
import cc.mrbird.common.domain.ResponseBo;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cc.mrbird.common.zk.ZKFPDemo.byteArrayToInt;
import static cc.mrbird.common.zk.ZKFPDemo.writeBitmap;

@Controller
public class zkweb {


    int fpWidth = 0;
    //the height of fingerprint image
    int fpHeight = 0;
    //for verify test
    private byte[] lastRegTemp = new byte[2048];
    //the length of lastRegTemp
    private int cbRegTemp = 0;
    //pre-register template
    private byte[][] regtemparray = new byte[3][2048];
    //Register
    private boolean bRegister = false;
    //Identify
    private boolean bIdentify = true;
    //finger id
    private int iFid = 1;

    private int nFakeFunOn = 1;
    //must be 3
    static final int enroll_cnt = 3;
    //the index of pre-register function
    private int enroll_idx = 0;

    private byte[] imgbuf = null;
    private byte[] template = new byte[2048];
    private int[] templateLen = new int[1];


    private boolean mbStop = true;
    private long mhDevice = 0;
    private long mhDB = 0;
    private WorkThread workThread = null;
    private String base=null;
    @Log("启动指纹识别")
    @RequestMapping("zk/start")
    @ResponseBody
    public ResponseBo start() {
        if (0 != mhDevice) {
            //already inited
            return  ResponseBo.error("Please close device first!");
        }
        int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
        //Initialize
        cbRegTemp = 0;
        bRegister = false;
        bIdentify = false;
        iFid = 1;
        enroll_idx = 0;
        if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init()) {


            return  ResponseBo.error("Init failed!");
        }
        ret = FingerprintSensorEx.GetDeviceCount();
        if (ret < 0) {

            workThread.FreeSensor();

            return  ResponseBo.error("No devices connected!");
        }
        if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0))) {

            workThread.FreeSensor();

            return  ResponseBo.error("Open device fail, ret = " + ret + "!");
        }
        if (0 == (mhDB = FingerprintSensorEx.DBInit())) {

            workThread.FreeSensor();

            return  ResponseBo.error("Init DB fail, ret = " + ret + "!");
        }

        //For ISO/Ansi
        int nFmt = 0;    //Ansi

        FingerprintSensorEx.DBSetParameter(mhDB, 5010, nFmt);
        //For ISO/Ansi End

        //set fakefun off
        //FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);

        byte[] paramValue = new byte[4];
        int[] size = new int[1];
        //GetFakeOn
        //size[0] = 4;
        //FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
        //nFakeFunOn = byteArrayToInt(paramValue);

        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
        fpWidth = byteArrayToInt(paramValue);
        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
        fpHeight = byteArrayToInt(paramValue);
        //width = fingerprintSensor.getImageWidth();
        //height = fingerprintSensor.getImageHeight();
        imgbuf = new byte[fpWidth * fpHeight];
        // btnImg.resize(fpWidth, fpHeight);
        mbStop = false;
        workThread = new WorkThread();
        workThread.start();// 线程启动
        return ResponseBo.ok();

    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            int ret = 0;
            while (!mbStop) {
                templateLen[0] = 2048;
                if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen))) {
                    if (nFakeFunOn == 1) {
                        byte[] paramValue = new byte[4];
                        int[] size = new int[1];
                        size[0] = 4;
                        int nFakeStatus = 0;
                        //GetFakeStatus
                        ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
                        nFakeStatus = byteArrayToInt(paramValue);
                        System.out.println("ret = " + ret + ",nFakeStatus=" + nFakeStatus);
                        if (0 == ret && (byte) (nFakeStatus & 31) != 31) {

                            // return "Is a fake-finer?";
                        }
                    }
                    OnCatpureOK(imgbuf);
                    OnExtractOK(template, templateLen[0]);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private void FreeSensor() {
            mbStop = true;
            try {        //wait for thread stopping
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (0 != mhDB) {
                FingerprintSensorEx.DBFree(mhDB);
                mhDB = 0;
            }
            if (0 != mhDevice) {
                FingerprintSensorEx.CloseDevice(mhDevice);
                mhDevice = 0;
            }
            FingerprintSensorEx.Terminate();
        }

        private void OnCatpureOK(byte[] imgBuf) {
            try {
                writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
                //  btnImg.setIcon(new ImageIcon(ImageIO.read(new File("fingerprint.bmp"))));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private String OnExtractOK(byte[] template, int len) {

            if (bRegister) {
                int[] fid = new int[1];
                int[] score = new int[1];
                int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
                if (ret == 0) {

                    bRegister = false;
                    enroll_idx = 0;
                    return "the finger already enroll by " + fid[0] + ",cancel enroll";
                }
                if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx - 1], template) <= 0) {

                    return "please press the same finger 3 times for the enrollment";
                }
                System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
                enroll_idx++;
                if (enroll_idx == 3) {
                    int[] _retLen = new int[1];
                    _retLen[0] = 2048;
                    byte[] regTemp = new byte[_retLen[0]];

                    if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2], regTemp, _retLen)) &&
                            0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
                        iFid++;
                        cbRegTemp = _retLen[0];
                        System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
                        //Base64 Template
                        base= FingerprintSensorEx.BlobToBase64(lastRegTemp, cbRegTemp);


                        // textArea.setText("enroll succ");
                    } else {

                        //  textArea.setText("enroll fail, error code=" + ret);
                    }
                    bRegister = false;
                } else {

                    //  textArea.setText("You need to press the " + (3 - enroll_idx) + " times fingerprint");
                }
            } else {
                if (bIdentify) {
                    int[] fid = new int[1];
                    int[] score = new int[1];
                    int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
                    if (ret == 0) {

                        // textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0]);
                    } else {

                        //   textArea.setText("Identify fail, errcode=" + ret);
                    }

                } else {
                    if (cbRegTemp <= 0) {

                        // textArea.setText("Please register first!");
                    } else {
                        int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, template);
                        if (ret > 0) {

                            //   textArea.setText("Verify succ, score=" + ret);
                        } else {

                            //  textArea.setText("Verify fail, ret=" + ret);
                        }
                    }
                }
            }
           return "error";

        }

    }

    @Log("启动指纹登记")
    @RequestMapping("zk/lu")
    @ResponseBody
    public ResponseBo actionPerformed() {
        Map map=new HashMap();
        if (0 == mhDevice) {

            return ResponseBo.error("Please Open device first!");
        }
        if (!bRegister) {
            enroll_idx = 0;
            bRegister = true;

            return ResponseBo.ok();
        }else
            return ResponseBo.error();

    }
    @Log("关闭设备")
    @RequestMapping("zk/end")
    @ResponseBody
    public ResponseBo end() {
        // TODO Auto-generated method stub
        workThread.FreeSensor();
        return ResponseBo.ok();

    }

    @Log("查询指纹登记情况")
    @RequestMapping("zk/check")
    @ResponseBody
    public ResponseBo check() {
        Map map=new HashMap();
        if (0 == mhDevice) {
            return ResponseBo.error("Please Open device first!");

        }
        if (enroll_idx==3) {
            map.put("times",enroll_idx);
            map.put("base",base);
            return ResponseBo.ok(map);
        }else{
            map.put("times",enroll_idx);
            map.put("base","");
            return ResponseBo.error(map);
        }

    }

    @Log("查询指纹登记情况获取指纹图版本")
    @RequestMapping("zk/checkpic")
    @ResponseBody
    public ResponseBo checkPic(String PrionerNo) {
        Map map=new HashMap();
        if (0 == mhDevice) {
            return ResponseBo.error("Please Open device first!");

        }
        if (enroll_idx==3) {
            Date date = new Date();
            int month = date.getMonth();
            int months = month + 1;
            String mon = String.valueOf(months);
            String result=   Base.imageToBase64Str("fingerprint.bmp");
            Base.CreateImage(result,"D://"+PrionerNo+"//"+mon+"//fingerprint.png","D://"+PrionerNo+"//"+mon);
            map.put("times",enroll_idx);
            map.put("base",base);
            return ResponseBo.ok(map);
        }else{
            map.put("times",enroll_idx);
            map.put("base","");
            return ResponseBo.error(map);
        }

    }
}
