package com.example.swaggerguide.controller;

import com.example.swagger_guide.api.facade.MembersApi;
import com.example.swagger_guide.api.model.MemberDTO;
import com.example.swagger_guide.api.model.MemberRoles;
import com.example.swagger_guide.api.model.TaskDTO;
import com.example.swaggerguide.entity.Member;
import com.example.swaggerguide.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
@Controller
public class MemberController implements MembersApi {
    private MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostConstruct
    public void populate(){
        List<MemberDTO> memberDTOs = new ArrayList<>();

        memberDTOs.add(new MemberDTO("johndoe1", "John", "Doe", "johndoe@example.com", MemberRoles.EMPLOYEE));
        memberDTOs.add(new MemberDTO("janesmith2", "Jane", "Smith", "janesmith@example.com", MemberRoles.MANAGER));
        memberDTOs.add(new MemberDTO("jamesjohnson3", "James", "Johnson", "jamesjohnson@example.com", MemberRoles.ADMIN));
        memberDTOs.add(new MemberDTO("emilydavis4", "Emily", "Davis", "emilydavis@example.com", MemberRoles.EMPLOYEE));
        memberDTOs.add(new MemberDTO("michaelbrown5", "Michael", "Brown", "michaelbrown@example.com", MemberRoles.MANAGER));
        memberDTOs.add(new MemberDTO("sophialee6", "Sophia", "Lee", "sophialee@example.com", MemberRoles.ADMIN));
        memberDTOs.add(new MemberDTO("williamtaylor7", "William", "Taylor", "williamtaylor@example.com", MemberRoles.EMPLOYEE));
        memberDTOs.add(new MemberDTO("oliviawilson8", "Olivia", "Wilson", "oliviawilson@example.com", MemberRoles.MANAGER));
        memberDTOs.add(new MemberDTO("ethansmith9", "Ethan", "Smith", "ethansmith@example.com", MemberRoles.ADMIN));
        memberDTOs.add(new MemberDTO("avabrown10", "Ava", "Brown", "avabrown@example.com", MemberRoles.EMPLOYEE));

        memberDTOs.stream().forEach(memberService::addMember);
    }

    @Override
    public CompletableFuture<ResponseEntity<MemberDTO>> createMember(MemberDTO memberDTO) {
        Optional<MemberDTO> result = memberService.addMember(memberDTO);
        if(result.isEmpty()){
            return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(HttpStatusCode.valueOf(400)));
        }
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(result.get()));
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteMember(String memberId) {
        memberService.deleteMember(memberId);
        return MembersApi.super.deleteMember(memberId);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<MemberDTO>>> getAllMembers(Integer limit) {
        List<MemberDTO> result = memberService.getAll();
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(result));
    }

    @Override
    public CompletableFuture<ResponseEntity<MemberDTO>> getMemberByID(String memberId) {
        return MembersApi.super.getMemberByID(memberId);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<TaskDTO>>> getMemberTasks(String memberId, Integer limit) {
        return MembersApi.super.getMemberTasks(memberId, limit);
    }

    @Override
    public CompletableFuture<ResponseEntity<MemberDTO>> updateMember(String memberId, MemberDTO memberDTO) {
        return MembersApi.super.updateMember(memberId, memberDTO);
    }
}
