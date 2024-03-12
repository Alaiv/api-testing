package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import factories.PetFactory;
import io.restassured.response.Response;
import models.PetDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import services.PetService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreatePetTests extends BaseTest {
    private PetService petService;
    private PetFactory petFactory;
    private final String messagePath = "message";
    private final String notFoundMessage = "Pet not found";

    @BeforeAll
    public void initServices() {
        petService = new PetService(settingsConfig.baseUrl());
        petFactory = new PetFactory();
    }

    @Test
    public void Create_WithValidValues_isSuccessful() {
        PetDto expectedPet = petFactory.getBasicPetWithValidData();

        Response response = petService.addPet(expectedPet);
        PetDto actualPetDto = response.getBody().as(PetDto.class);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(expectedPet.getName(), actualPetDto.getName());
    }

    @Test
    public void Get_ExistingPet_isSuccessful() {
        PetDto expectedPet = petFactory.getBasicPetWithValidData();
        petService.addPet(expectedPet);

        PetDto actualPet = petService.getPet(expectedPet.getId()).as(PetDto.class);

        Assertions.assertNotNull(actualPet);

        boolean petsAreEqual = expectedPet.equals(actualPet);
        Assertions.assertTrue(petsAreEqual);
    }

    @Test
    public void Delete_ExistingPet_isSuccessful() {
        PetDto expectedPet = petFactory.getBasicPetWithValidData();
        petService.addPet(expectedPet);

        Response deleteResponse = petService.deletePet(expectedPet.getId());
        Assertions.assertEquals(200, deleteResponse.statusCode());
        Assertions.assertEquals(Integer.toString(expectedPet.getId()), deleteResponse.jsonPath().get(messagePath));

        Response getResponse = petService.getPet(expectedPet.getId());
        Assertions.assertEquals(404, getResponse.getStatusCode());
        Assertions.assertEquals(notFoundMessage, getResponse.jsonPath().get(messagePath));
    }
}
