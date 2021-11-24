package common.utils;

import aquality.selenium.browser.AqualityServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.models.PostResponse;
import common.models.ServerAddressResponse;
import common.models.UploadResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class VkApiUtils {

    private static final String token = ConfigUtil.getToken();
    private static final String albumId = ConfigUtil.getAlbum();
    private static final String userId = ConfigUtil.getUser();

    public static int makePost(String message) {
        AqualityServices.getLogger().info("Making a post with " + message + " text");
        String url =
            "https://api.vk.com/method/wall.post?message="
                + message
                + "&access_token="
                + token
                + "&v=5.131";
        String fromUrl = ApiUtils.getStringFromUrl(url);
        PostResponse response;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(fromUrl, PostResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return response.getPostId();
    }

    public static ServerAddressResponse getServer(String link) {
        String fromUrl = ApiUtils.getStringFromUrl(link);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fromUrl, ServerAddressResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static UploadResponse postFile(String url, File file) {
        String s = ApiUtils.postFileToUrl(url, file);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(s, UploadResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void changeMessageAndAddPhoto(File file, String message, int postId) {

        AqualityServices.getLogger().info("Changing post "+ postId+ " text to " + message + " and adding image " + file.getName());

        String getServer = "https://api.vk.com/method/photos.getUploadServer?&album_id="
            + albumId
            +"&access_token="
            + token
            + "&v=5.131";

        ServerAddressResponse serverAddressResponse = getServer(getServer);
        UploadResponse uploadResponse = postFile(serverAddressResponse.getServerUrl(), file);

        String photosListEncoded = "";
        try {
            photosListEncoded = URLEncoder
                .encode(uploadResponse.getPhotos_list(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String photosSave = "https://api.vk.com/method/photos.save?"
            + "&album_id="
            + albumId
            + "&access_token="
            + token
            + "&server="
            + uploadResponse.getServer()
            + "&photos_list="
            + photosListEncoded
            + "&hash="
            + uploadResponse.getHash()
            + "&v=5.131";

        JSONObject body = ApiUtils.getJsonFromUrl(photosSave);
        JSONArray array = (JSONArray) body.get("response");
        JSONObject object = (JSONObject) array.get(0);
        String photoId = object.getString("id");

        String wallEdit = "https://api.vk.com/method/wall.edit?post_id="
            + postId
            + "&message="
            + message
            + "&attachments=photo"
            + userId
            + "_"
            + photoId
            + "&access_token="
            + token
            + "&v=5.131";

        ApiUtils.getFromUrl(wallEdit);
    }

    public static void leaveCommentToPost(int postId, String message) {

        AqualityServices.getLogger().info("Leaving a comment with text " + message + " to post " + postId);

        String createComment = "https://api.vk.com/method/wall.createComment?post_id="
            + postId
            + "&message="
            + message
            + "&access_token="
            + token
            + "&v=5.131";

        ApiUtils.getFromUrl(createComment);
    }

    public static boolean checkLike(int postId) {

        AqualityServices.getLogger().info("Checking like existence on post " + postId);

        String link = "https://api.vk.com/method/likes.getList?type=post"
            + "&owner_id="
            + userId
            + "&item_id="
            + postId
            + "&access_token="
            + token
            + "&v=5.131";

        JSONObject body = ApiUtils.getJsonFromUrl(link);
        JSONObject object = (JSONObject) body.get("response");
        JSONArray array = (JSONArray) object.get("items");
        String likeId = array.getString(0);

        return userId.equals(likeId);
    }

    public static void deletePost(int postId) {

        AqualityServices.getLogger().info("Deleting post " + postId);

        String link = "https://api.vk.com/method/wall.delete?post_id="
            + postId
            + "&access_token="
            + token
            + "&v=5.131";

        ApiUtils.getFromUrl(link);
    }
}
