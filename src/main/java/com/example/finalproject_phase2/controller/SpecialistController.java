package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.specialistDto.ConvertImageDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistLoginDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistScoreDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.StatusOrderSpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.ValidSpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import com.example.finalproject_phase2.mapper.OrdersMapper;
import com.example.finalproject_phase2.mapper.SpecialistMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/specialist")
public class SpecialistController {
    private final SpecialistService specialistService;

    private final OrdersService ordersService;
    private final CustomerCommentsService customerCommentsService;
    private final SpecialistSuggestionService specialistSuggestionService;

    private final OrdersMapper ordersMapper;
    private final SpecialistMapper specialistMapper;

    @Autowired
    public SpecialistController(SpecialistService specialistService, CustomerCommentsService customerCommentsService, OrdersService ordersService, SpecialistSuggestionService specialistSuggestionService, OrdersMapper ordersMapper, SpecialistMapper specialistMapper) {
        this.specialistService = specialistService;
        this.customerCommentsService = customerCommentsService;
        this.ordersService = ordersService;
        this.specialistSuggestionService = specialistSuggestionService;
        this.ordersMapper = ordersMapper;
        this.specialistMapper = specialistMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid SpecialistDto specialistDto){
        System.out.println(specialistMapper.specialistDtoToSpecialist(specialistDto).getEmail());
        return  ResponseEntity.ok(specialistService.register(specialistMapper.specialistDtoToSpecialist(specialistDto)));
    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody SpecialistLoginDto specialistLoginDto
            , @RequestParam String userType){
        CheckValidation.userType=userType;
        System.out.println(userType);
        if (userType.equals("specialist")){
            return  ResponseEntity.ok(specialistService.authenticate(specialistLoginDto));
        }else  return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/signUp")
    public ResponseEntity<SpecialistDto> addSpecialist(@RequestBody @Valid SpecialistDto specialistDto) {
        System.out.println(specialistDto);
        SpecialistDto specialistDtoCandidate = specialistService.addSpecialist(specialistDto);
        if (specialistDtoCandidate!=null)return new ResponseEntity<>(specialistDtoCandidate, HttpStatus.ACCEPTED);
        else throw new CustomException("specialist not saved");
    }
    @PostMapping("/login")
    public ResponseEntity<SpecialistDto> loginByEmailAndPassword(@RequestBody @Valid SpecialistLoginDto specialistLoginDto) {
        SpecialistDto specialistDto = specialistService.loginByEmailAndPassword(specialistLoginDto);
        if (specialistDto!=null)return new ResponseEntity<>(specialistDto, HttpStatus.ACCEPTED);
        else throw new CustomException("specialist not found");
    }
    @PostMapping("/confirmByAdmin")
    public ResponseEntity<SpecialistDto> confirmSpecialistByAdmin(@RequestBody @Valid SpecialistDto specialistDto) {
        SpecialistDto specialistDtoCandidate = specialistService.confirmSpecialistByAdmin(specialistDto);
        if (specialistDtoCandidate!=null)return new ResponseEntity<>(specialistDtoCandidate, HttpStatus.ACCEPTED);
        else throw new CustomException("confirm by admin have error");
    }
    @PostMapping("/updateScore")
    public ResponseEntity<Integer> updateScore(@RequestBody @Valid SpecialistScoreDto specialistScoreDto) {
        Integer score  = specialistService.updateSpecialistScore(specialistScoreDto);
        if (score!=null)return new ResponseEntity<>(score, HttpStatus.ACCEPTED);
        else throw new CustomException("score has not been updated");
    }
    @PostMapping("/showImage")
    public ResponseEntity<String> showImage(@RequestBody @Valid ConvertImageDto convertImageDto) {
        try {
            specialistService.convertByteArrayToImage(convertImageDto);
            return new ResponseEntity<>("image is converted", HttpStatus.ACCEPTED);
        } catch (CustomException ce) {
            throw new CustomException(ce.getMessage());
        }
    }
        @PostMapping("/search")
        public ResponseEntity<List<Specialist>> searchSpecialist(@RequestBody SpecialistDto specialistDto) {
            List<Specialist> specialists = specialistService.searchSpecialist(specialistDto);
           CheckValidation.memberTypespecialist= specialists.get(0);
            System.out.println(CheckValidation.memberTypespecialist.getEmail());
            return new ResponseEntity<>(specialists, HttpStatus.ACCEPTED);
        }

    @PostMapping("/showOrdersToSpecialist")
    public ResponseEntity<Collection<OrdersDto>> showOrdersToSpecialist(@RequestBody @Valid SubDutyDto subDutyDto ) {
        Collection<Orders> ordersCollection = ordersService.showOrdersToSpecialist(subDutyDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/showScore")
    public ResponseEntity<Integer> showScore(@RequestBody @Valid SpecialistDto specialistDto ) {
        Integer score = customerCommentsService.showScoreOfLastCustomerCommentsThatThisSpecialistIsExist(
                specialistMapper.specialistDtoToSpecialist(specialistDto));
        return new ResponseEntity<>(score, HttpStatus.ACCEPTED);
    }
    @PostMapping("/submitSpecialSuggestion")
    public ResponseEntity<Boolean> IsValidSpecialSuggestion(@RequestBody @Valid ValidSpecialistSuggestionDto validSpecialistSuggestionDto) {
        specialistSuggestionService.IsValidSpecialSuggestion(validSpecialistSuggestionDto);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findSuggestWithThisSpecialistAndOrder")
    public ResponseEntity<Boolean> findSuggestWithThisSpecialistAndOrder(@RequestBody @Valid StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist ) {
        specialistSuggestionService.findSuggestWithThisSpecialistAndOrder(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/changeSpecialistSelectedOfOrder")
    public ResponseEntity<SpecialistSelectionOfOrder> changeSpecialistSelectedOfOrder(@RequestBody @Valid SpecialistSelectionOfOrder specialistSelectionOfOrder  ) {
        SpecialistSelectionOfOrder specialistSelectionOfOrderCandidate = specialistSuggestionService.changeSpecialistSelectedOfOrder(specialistSelectionOfOrder);
        return new ResponseEntity<>(specialistSelectionOfOrderCandidate, HttpStatus.ACCEPTED);
    }

    @PostMapping("/changeStatusOrderToWaitingForSpecialistToWorkplace")
    public ResponseEntity<Boolean> changeStatusOrderToWaitingForSpecialistToWorkplace(@RequestBody @Valid  StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist  statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist ) {
        specialistSuggestionService.changeStatusOrderToWaitingForSpecialistToWorkplace(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
}
