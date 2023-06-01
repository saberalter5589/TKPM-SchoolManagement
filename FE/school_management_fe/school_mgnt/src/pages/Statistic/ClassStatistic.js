import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const ClassStatistic = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [classId, setClassId] = useState();
  const [classes, setClasses] = useState();
  const [classData, setClassData] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    loadClass();
  }, []);

  useEffect(() => {
    const params = {
      classId: classId,
    };
    loadClassData(params);
  }, [classId]);

  const loadClass = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/class/search",
      config
    );
    setClasses(response?.data?.classDtoList);
    setClassId(response?.data?.classDtoList[0]?.classId);
  };

  const loadClassData = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },

      params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/statistic/class",
      config
    );
    setClassData(response?.data?.semesterDtoList);
  };

  return (
    <div className="col-lg-12">
      <form>
        <div className="card">
          <div className="card-header">
            <h1>Class Statistic</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Class</label>
                  <select
                    value={classId}
                    onChange={(e) => setClassId(e.target.value)}
                    className="form-control"
                  >
                    {classes?.map((cls) => (
                      <option value={cls?.classId}>
                        {`${cls?.classCode} - ${cls?.className}`}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
      </form>
      <h2>Student score list</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Student Id</th>
              <th scope="col">Name</th>
              <th scope="col">First Semester Average</th>
              <th scope="col">Second Semester Average</th>
              <th scope="col">Final Average</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {classData?.map((cd, index) => (
              <>
                <tr>
                  <th scope="row" key={cd?.studentId}>
                    {cd?.studentId}
                  </th>
                  <td>{cd?.firstName + " " + cd?.lastName}</td>
                  <td>{cd?.firstSemAvgScore}</td>
                  <td>{cd?.secondSemAvgScore}</td>
                  <td>{cd?.finalSemAvgScore}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/student-statistic/${cd?.studentId}`}
                    >
                      View Detail
                    </Link>
                  </td>
                </tr>
              </>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ClassStatistic;
