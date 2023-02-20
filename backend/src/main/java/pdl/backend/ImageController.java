package pdl.backend;
import java.io.IOException;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ImageController {

  @Autowired
  private ObjectMapper mapper;

  private final ImageDao imageDao;

  @Autowired
  public ImageController(ImageDao imageDao) {
    this.imageDao = imageDao;
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable("id") long id) {
    Optional<Image> optionalImage = imageDao.retrieve(id);
    if (!optionalImage.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      byte[] bytes = optionalImage.get().getData();
      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
    Optional<Image> delImage = imageDao.retrieve(id);
    if(delImage.isPresent()){
      imageDao.delete(delImage.get());
      return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam() MultipartFile file,
      RedirectAttributes redirectAttributes) {
    Image img = null;
    System.out.println(file.getContentType());
    System.out.println(file.getName());
    if(file.getContentType().equals("image/jpeg") ||file.getContentType().equals("image/jpg") ){
      try {
        img = new Image(file.getOriginalFilename(),file.getBytes());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      imageDao.create(img);
      System.out.println("nice upload");
      return new ResponseEntity<>(HttpStatus.OK);

    }
    return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() {
    ArrayNode nodes = mapper.createArrayNode();
    List<Image> list=imageDao.retrieveAll();
    for(Image image : list) {
      ObjectNode node = mapper.createObjectNode();
      node.put("id", image.getId());
      node.put("name", image.getName());
      nodes.add(node);
    }

    return nodes;
  }
}
