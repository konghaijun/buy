package com.jiguang.supplement.controller;

import com.jiguang.commonutils.R;
import com.jiguang.supplement.config.BaiDu;
import com.jiguang.supplement.config.TengXun;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.tiia.v20190529.models.DetectProductBetaRequest;
import com.tencentcloudapi.tiia.v20190529.models.DetectProductBetaResponse;
import okhttp3.*;
import okhttp3.RequestBody;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;



import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
/**
 * @Auther: 23091
 * @Date: 2023/4/23 21:43
 * @Description:
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/supplement/baidu")
public class BaiDuController {

@PostMapping("token")
    public R getToken(@RequestParam("file") MultipartFile file) throws Exception{

    // Set up API Key (AK) and Secret Key (SK)
    String clientId = BaiDu.BAIDU_KEY_ID;
    String clientSecret = BaiDu.BAIDU_KEY_SECRET;

/*// Encode the credentials using Base64
    String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

// Set up URL for authentication
    String authUrl = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;

// Send HTTP request to authentication URL
    URL url = new URL(authUrl);

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);

// Read response from authentication URL
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
    in.close();

// Parse access token from response
    String accessToken = response.toString().split("\"")[3];*/


//https://aip.baidubce.com/oauth/2.0/token
 final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();



 //https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=E5GcSzGvm8K3Ij5MrUYYzt3g&client_secret=s8yRKKngDGFwEx7vejIhaUudlFHAz65o
 //https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=E5GcSzGvm8K3Ij5MrUYYzt3g&client_secret=s8yRKKngDGFwEx7vejIhaUudlFHAz65o
    String authUrl = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" +clientId+"&client_secret=" + clientSecret;
    System.out.println(authUrl);

    MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(authUrl)
                .method("POST", body)
               // .addHeader("Content-Type", "application/json")
              //  .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
      String  accessToken= response.body().string().split("\"")[3];

  //  System.out.println(response.body().string());








    //_______________________________________________________________________________

    // Import necessary libraries






            try {
                // Set up the URL and connection
                URL url1 = new URL("https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general");
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);





                byte[] imageData = file.getBytes();

                // Convert the image data to base64
                String base64Image = Base64.getEncoder().encodeToString(imageData);




                // Set up the request body
                String requestBody = "access_token="+accessToken+"&image="+base64Image;


                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer response1 = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    response1.append(line);
                }
                bufferedReader.close();
                inputStream.close();

                // Print the response
                System.out.println(response1.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }



    return R.ok().data("url",accessToken);
}



@PostMapping("gg")
    public R gg(@RequestParam("file") MultipartFile file){

    byte[] imageData = new byte[0];
    try {
        imageData = file.getBytes();
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Convert the image data to base64
    String base64Image = Base64.getEncoder().encodeToString(imageData);
    return R.ok().data("data",base64Image);
}





@PostMapping("images")
     public R getImages(@RequestParam("file") MultipartFile file) throws Exception {

    String SecretId = TengXun.TENGXUN_KEY_ID;
    String SecretKey = TengXun.TENGXUN_KEY_SECRET;

    //图片base64编码
    byte[] imageData = new byte[0];
    try {
        imageData = file.getBytes();
    } catch (IOException e) {
        e.printStackTrace();
    }
    // Convert the image data to base64
    String base64Image = Base64.getEncoder().encodeToString(imageData);


    // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
    Credential cred = new Credential(SecretId, SecretKey);
    // 实例化一个http选项，可选的，没有特殊需求可以跳过
    HttpProfile httpProfile = new HttpProfile();
    httpProfile.setEndpoint("tiia.tencentcloudapi.com");
    // 实例化一个client选项，可选的，没有特殊需求可以跳过
    ClientProfile clientProfile = new ClientProfile();
    clientProfile.setHttpProfile(httpProfile);

    // 实例化要请求产品的client对象,clientProfile是可选的
    TiiaClient client = new TiiaClient(cred, "ap-guangzhou", clientProfile);
    // 实例化一个请求对象,每个接口都会对应一个request对象
    DetectProductBetaRequest req = new DetectProductBetaRequest();
    req.setImageBase64(base64Image);

    DetectProductBetaResponse resp = client.DetectProductBeta(req);
    // 输出json格式的字符串回包
    System.out.println(DetectProductBetaResponse.toJsonString(resp));


    return R.ok().data("data",DetectProductBetaResponse.toJsonString(resp));
}

}
