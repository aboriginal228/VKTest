package common.models;

public class UploadResponse {

  String server;
  String photos_list;
  String hash;
  String aid;


  public String getPhotos_list() {
    return photos_list;
  }

  public String getHash() {
    return hash;
  }

  public String getServer() {
    return server;
  }

  public String getAid() {
    return aid;
  }
}
