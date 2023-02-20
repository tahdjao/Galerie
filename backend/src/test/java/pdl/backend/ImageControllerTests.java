package pdl.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.file.Files;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ImageControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void reset() {
  	// reset Image class static counter
  	ReflectionTestUtils.setField(Image.class, "count", Long.valueOf(0));
	}

	@Test
	@Order(1)
	public void getImageListShouldReturnSuccess() throws Exception {
		mockMvc.perform(get("/images")).andExpect(status().isOk());

	}

	@Test
	@Order(2)
	public void getImageShouldReturnNotFound() throws Exception {
		mockMvc.perform(get("/images/1")).andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	public void getImageShouldReturnSuccess() throws Exception {
		mockMvc.perform(get("/images/0")).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void deleteImagesShouldReturnMethodNotAllowed() throws Exception {
		mockMvc.perform(delete("/images")).andExpect(status().isMethodNotAllowed());
	}

	@Test
	@Order(5)
	public void deleteImageShouldReturnNotFound() throws Exception {
		mockMvc.perform(delete("/images/15")).andExpect(status().isNotFound());
	}

	@Test
	@Order(6)
	public void deleteImageShouldReturnSuccess() throws Exception {
		mockMvc.perform(delete("/images/0")).andExpect(status().isAccepted());

	}

	@Test
	@Order(7)
	public void createImageShouldReturnSuccess() throws Exception {

		ClassPathResource imgFile = new ClassPathResource("test.jpg");
		byte[] fileContent = Files.readAllBytes(imgFile.getFile().toPath());
		mockMvc.perform(MockMvcRequestBuilders.multipart("/images").file(new MockMultipartFile("file", "test.jpg", "image/jpg", fileContent))).andExpect(status().isOk());

	}

	@Test
	@Order(8)
	public void createImageShouldReturnUnsupportedMediaType() throws Exception {
		ClassPathResource imgFile = new ClassPathResource("test.png");
		byte[] fileContent = Files.readAllBytes(imgFile.getFile().toPath());
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.png", "image/png", fileContent);
		// Envoi de la requête et vérification du statut de réponse
		mockMvc.perform(multipart("/images").file(mockMultipartFile))
				.andExpect(status().isUnsupportedMediaType());

	}
	
}
