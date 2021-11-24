package common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class PostResponse {

  int postId;

  @JsonProperty("response")
  private void unpackNestedAddress(Map<String,Object> address) {
    this.postId = (int) address.get("post_id");
  }

  public int getPostId() {
    return postId;
  }
}
