import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import Student from "../Student/Student";

const StudentStatistic = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [studentData, setStudentData] = useState([]);
  const [className, setClassName] = useState([]);
  const [studentName, setStudentName] = useState([]);
  const [passResult, setPassResult] = useState("");

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    const params = {
      studentId: id,
    };
    loadStudentData(params);
  }, []);

  const loadStudentData = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/statistic/student-subject",
      config
    );
    setClassName(response?.data?.className);
    setStudentName(response?.data?.studentName);
    setStudentData(response?.data?.subjectDtoList);

    const totalSubject = response?.data?.subjectDtoList?.length;
    const totalPass = response?.data?.subjectDtoList?.filter(
      (s) => s.result == 0
    )?.length;
    setPassResult(totalPass + "/" + totalSubject);
  };

  const renderResult = (val) => {
    switch (val) {
      case 0:
        return "PASS";
      case 1:
        return "FAIL";
    }
  };

  return (
    <>
      <h2>Student Statistic</h2>
      <h5>Student name: {studentName}</h5>
      <h5>Class name: {className}</h5>
      <h5>Total pass: {passResult}</h5>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Subject</th>
              <th scope="col">First Semester Average</th>
              <th scope="col">Second Semester Average</th>
              <th scope="col">Final Average</th>
              <th scope="col">Required Average</th>
              <th scope="col">Result</th>
            </tr>
          </thead>
          <tbody>
            {studentData?.map((st, index) => (
              <>
                <tr>
                  <td>{st?.subjectName}</td>
                  <td>{st?.firstSemSubAvgScore}</td>
                  <td>{st?.secondSemSubAvgScore}</td>
                  <td>{st?.finalSubAvgScore}</td>
                  <td>{st?.subjectAvgScore}</td>
                  <td>{renderResult(st?.result)}</td>
                </tr>
              </>
            ))}
          </tbody>
        </table>
        <Link className="btn btn-danger" to={"/class-statistic"}>
          Back
        </Link>
      </div>
    </>
  );
};

export default StudentStatistic;
