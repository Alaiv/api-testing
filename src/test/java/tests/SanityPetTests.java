package tests;

import factories.PetFactory;
import io.restassured.response.Response;
import models.PetDto;
import org.junit.jupiter.api.*;
import services.PetService;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SanityPetTests extends BaseTest {
    private PetService petService;
    private PetFactory petFactory;
    private final String messagePath = "message";
    private final String notFoundMessage = "Pet not found";
    private List<Integer> idsForDelete = new ArrayList<>();

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

        idsForDelete.add(expectedPet.getId());
    }

    @Test
    public void Get_ExistingPet_isSuccessful() {
        PetDto expectedPet = petFactory.getBasicPetWithValidData();
        petService.addPet(expectedPet);

        PetDto actualPet = petService.getPet(expectedPet.getId()).as(PetDto.class);

        Assertions.assertNotNull(actualPet);

        boolean petsAreEqual = expectedPet.equals(actualPet);
        Assertions.assertTrue(petsAreEqual);

        idsForDelete.add(expectedPet.getId());
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

    @Test
    public void Update_ExistingPet_isSuccessful() {
        PetDto petForUpdate = petFactory.getBasicPetWithValidData();
        petService.addPet(petForUpdate);

        String newPetName = "new_name_for_test";
        petForUpdate.setName(newPetName);

        petService.updatePet(petForUpdate);

        PetDto updatedPet = petService.getPet(petForUpdate.getId()).as(PetDto.class);

        Assertions.assertNotNull(updatedPet);
        Assertions.assertEquals(newPetName, updatedPet.getName());

        idsForDelete.add(petForUpdate.getId());
    }

    @AfterAll
    public void tearDown() {
        idsForDelete.forEach(id -> petService.deletePet(id));
    }
}
