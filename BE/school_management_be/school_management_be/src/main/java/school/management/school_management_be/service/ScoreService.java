package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.score.CreateScoreRequest;
import school.management.school_management_be.dto.request.score.GetScoreRequest;
import school.management.school_management_be.dto.response.score.GetScoreResponse;

public interface ScoreService {
    void createScore(CreateScoreRequest request);
    GetScoreResponse getScores(GetScoreRequest request);
}
