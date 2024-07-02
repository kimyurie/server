package com.saeromteo.app.controller.collection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.saeromteo.app.model.collection.CollectionDto.ReadAllDto;
import com.saeromteo.app.model.collection.CollectionDto.ReadCollectionResponse;
import com.saeromteo.app.model.collection.CollectionDto.RegistRequest;
import com.saeromteo.app.model.user.UserInfoDTO.UserResponse;
import com.saeromteo.app.model.collection.CollectionEntity;
import com.saeromteo.app.service.collection.CollectionService;
import com.saeromteo.app.service.user.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/collection")
@Api(tags = "Collection Management")
public class CollectionApiController {

	@Autowired
	CollectionService collectionService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/read-user")
	@ApiOperation(value = "유저정보 조회", notes = "유저 정보를 조회한다.")
	public UserResponse readUser() {
		UserResponse userData = userService.readUserforCollection(1);
		return userData;
	}

	@PostMapping("/registration")
	@ApiOperation(value = "수거 신청", notes = "사용자가 수거 서비스를 신청한다.")
	public String registration(@RequestBody RegistRequest registRequest) {
		System.out.println("Name: " + registRequest.getName());
		System.out.println("Phone: " + registRequest.getPhone());
		System.out.println("Address: " + registRequest.getAddress());
		System.out.println("Detail Address: " + registRequest.getDetailAddress());

		// 유저 주소, 전화번호 수정
//		collectionService.registration(submitRequest);

		return "데이터가 성공적으로 제출되었습니다.";
	}

	@PostMapping("/request")
	@ApiOperation(value = "수거 요청", notes = "사용자의 수거 요청을 등록한다.")
	public void request(@ModelAttribute RegistRequest registRequest,
			@RequestParam("images") List<MultipartFile> images) {
		collectionService.request(registRequest, images);
	}

	@GetMapping("/approve")
	@ApiOperation(value = "수거 요청 접수", notes = "수거 요청을 접수한다.")
	public RedirectView approve(@RequestParam("collectionId") Integer collectionId) {
		System.out.println("collectionId: " + collectionId);
		collectionService.approve(collectionId);
		
		return new RedirectView("/app/admin/collection-manager");
	}
	
	@GetMapping("/complete")
	@ApiOperation(value = "수거 완료", notes = "수거를 완료한다.")
	public RedirectView complete(@RequestParam("collectionId") Integer collectionId, @RequestParam("weight") Float weight) {
		System.out.println("collectionId: " + collectionId);
		System.out.println("weight: " + weight);
		
		collectionService.complete(collectionId, weight);
		
		return new RedirectView("/app/admin/collection-manager");
	}
	
	@GetMapping("/read-collection")
	@ApiOperation(value = "유저별 수거내역 조회", notes = "유저별 수거내역을 조회한다.")
	public List<ReadCollectionResponse> readCollection() {
		List<ReadCollectionResponse> readCollectionResponse = collectionService.readByUserId(1);
		
		return readCollectionResponse;
	}
	
	@GetMapping("/read-all-collection")
	@ApiOperation(value = "모든 수거내역 조회", notes = "모든 수거내역을 조회한다.")
	public List<ReadAllDto> collection() {
		List<ReadAllDto> collectionList = collectionService.readAllForAdmin();
		return collectionList;
	}
	
	
	
	
	
//	@PostMapping("/predict-images")
//	@ApiOperation(value = "이미지 분석", notes = "사용자가 수거 서비스를 신청한다.")
//	public PredictResponse processData(@RequestBody PredictRequest predictRequest) {
//		return collectionService.postDataToApi(predictRequest);
//	}

	@PostMapping("/insert")
	@ApiOperation(value = "수거 등록", notes = "수거 정보를 등록한다.")
	public String insertCollection(@RequestBody CollectionEntity collectionEntity) {
		int result = collectionService.insertCollection(collectionEntity);
		return result + "건 입력됨";
	}

	@PutMapping(value = "/update", consumes = "application/json", produces = "text/plain;charset=UTF-8")
	@ApiOperation(value = "수거 수정", notes = "수거 정보를 수정한다.")
	public String updateCollection(@RequestBody CollectionEntity collectionEntity) {
		int result = collectionService.updateCollection(collectionEntity);
		return result + "건 수정됨";
	}

	@DeleteMapping("/delete/{collectionId}")
	@ApiOperation(value = "수거 삭제", notes = "수거 정보를 삭제한다.")
	public String deleteCollection(@PathVariable("collectionId") String collectionId) {
		int result = collectionService.deleteCollection(collectionId);
		return result + "건 삭제됨";
	}

}
