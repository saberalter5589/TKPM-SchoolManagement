package school.management.school_management_be.dto.response.subject;

import lombok.Data;
import school.management.school_management_be.dto.obj.SubjectDto;

import java.util.List;

@Data
public class GetSubjectResponse {
    List<SubjectDto> subjectDtoList;
}
