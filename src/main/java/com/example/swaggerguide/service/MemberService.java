package com.example.swaggerguide.service;

import com.example.swagger_guide.api.model.MemberDTO;
import com.example.swaggerguide.entity.Member;
import com.example.swaggerguide.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<MemberDTO> getAll(){
        return memberRepository
                .findAll()
                .stream()
                .map(MemberService::generateDTO)
                .collect(Collectors.toList());
    }

    public Optional<MemberDTO> addMember(MemberDTO memberDTO){
        if(!validateMemberDTO(memberDTO)){
            return Optional.empty();
        }
        Member m = generateMember(memberDTO);
        return Optional.of(generateDTO(memberRepository.save(m)));
    }

    public void deleteMember(String id){
        memberRepository.deleteById(id);
    }

    public static MemberDTO generateDTO(Member member){
        return new MemberDTO()
                .id(member.getId())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .email(member.getEmail())
                .role(member.getRole());
    }

    public static boolean validateMemberDTO(MemberDTO memberDTO){
        // id must start with a letter followed by letters or numbers, the size must be between 5 and 20
        Pattern id = Pattern.compile("[a-zA-Z].{4,19}");
        // email must have a sequence of letters flowed by an @ with at least one . followed by a sequence of letters
        Pattern email = Pattern.compile("[a-zA-Z]*\\Q@\\E[a-zA-Z]*(\\Q.\\E[a-zA-Z]*)+");
        // name must be only letters
        Pattern name = Pattern.compile("[a-zA-Z]{2,20}");

        return id.matcher(memberDTO.getId()).matches() &
                email.matcher(memberDTO.getEmail()).matches() &
                name.matcher(memberDTO.getFirstName()).matches() &
                name.matcher(memberDTO.getLastName()).matches();
    }
    public static Member generateMember(MemberDTO memberDTO){
        return new Member(memberDTO.getId().toLowerCase().trim(),
                memberDTO.getFirstName().toLowerCase().trim(),
                memberDTO.getLastName().toLowerCase().trim(),
                memberDTO.getEmail().toLowerCase().trim(),
                memberDTO.getRole());
    }
}
