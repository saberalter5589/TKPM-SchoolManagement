package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.management.school_management_be.dto.obj.ScorePartDto;
import school.management.school_management_be.dto.request.score.CreateScoreRequest;
import school.management.school_management_be.dto.request.score.GetScoreRequest;
import school.management.school_management_be.dto.response.score.GetScoreResponse;
import school.management.school_management_be.entity.Score;
import school.management.school_management_be.mapper.EntityDxo;
import school.management.school_management_be.repository.ScoreRepository;
import school.management.school_management_be.service.ScoreService;
import school.management.school_management_be.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;


@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreRepository scoreRepository;

    @Override
    @Transactional
    public void createScore(CreateScoreRequest request) {
        Long authId = request.getAuthentication().getUserId();

        Long studentId = request.getStudentId();
        Long subjectId = request.getSubjectId();
        Long semester = request.getSemester();

        List<Score> currentScoreList = scoreRepository.findStudentScore(studentId, subjectId, semester);
        if(currentScoreList.size() > 0){
            scoreRepository.deleteAll(currentScoreList);
        }

        List<ScorePartDto> reqScoreParts = request.getScoreParts();
        for(ScorePartDto scorePartDto : reqScoreParts){
            Score score = new Score();
            score.setStudentId(studentId);
            score.setSubjectId(subjectId);
            score.setSemester(semester);
            score.setScoreType(scorePartDto.getScoreType());
            score.setScore(scorePartDto.getScore());
            score.setIsDeleted(false);
            EntityDxo.preCreate(authId, score);
            scoreRepository.save(score);
        }
    }

    @Override
    public GetScoreResponse getScores(GetScoreRequest request) {
        Long studentId = request.getStudentId();
        Long subjectId = request.getSubjectId();
        Long semester = request.getSemester();

        List<Score> currentScoreList = scoreRepository.findStudentScore(studentId, subjectId, semester);
        List<ScorePartDto> reqScoreParts = new ArrayList<>();
        if(CommonUtil.isNullOrEmpty(currentScoreList)){
            for(int i = 0; i <= 2; i++){
                ScorePartDto partDto = new ScorePartDto();
                partDto.setScoreType((long) i);
                partDto.setScore(0.0);
                reqScoreParts.add(partDto);
            }
        } else {
            for(Score score : currentScoreList){
                ScorePartDto partDto = new ScorePartDto();
                partDto.setScoreType(score.getScoreType());
                partDto.setScore(score.getScore());
                reqScoreParts.add(partDto);
            }
        }
        GetScoreResponse response = new GetScoreResponse();
        response.setScoreParts(reqScoreParts);
        return response;
    }
}
