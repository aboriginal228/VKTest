package common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ServerAddressResponse {

  String serverUrl;

  @JsonProperty("response")
  private void unpackNestedAddress(Map<String,Object> address) {
    this.serverUrl = (String) address.get("upload_url");
  }

  public String getServerUrl() {
    return serverUrl;
  }
}
