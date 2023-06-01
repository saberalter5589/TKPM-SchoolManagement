import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const ScoreDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [subjectList, setSubjectList] = useState([]);
  const [subjectId, setSubjectId] = useState();
  const [semesterId, setSemesterId] = useState(1);
  const [test15Score, setTest15Score] = useState();
  const [test45Score, setTest45Score] = useState();
  const [testFinalScore, setTestFinalScore] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    setSemesterId(1);
    loadSubject();
  }, []);

  useEffect(() => {
    const params = {
      studentId: id,
      subjectId: subjectId,
      semester: semesterId,
    };
    getScore(params);
  }, [subjectId, semesterId]);

  const loadSubject = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/subject/search",
      config
    );
    setSubjectList(response?.data?.subjectDtoList);
    setSubjectId(response?.data?.subjectDtoList[0]?.subjectId);
  };

  const getScore = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/score/search",
      config
    );
    const scoreParts = response?.data?.scoreParts;
    setTest15Score(scoreParts?.find((sp) => sp?.scoreType == 0)?.score);
    setTest45Score(scoreParts?.find((sp) => sp?.scoreType == 1)?.score);
    setTestFinalScore(scoreParts?.find((sp) => sp?.scoreType == 2)?.score);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const scoreParts = [
        {
          scoreType: 0,
          score: test15Score,
        },
        {
          scoreType: 1,
          score: test45Score,
        },
        {
          scoreType: 2,
          score: testFinalScore,
        },
      ];
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        studentId: id,
        subjectId: subjectId,
        semester: semesterId,
        scoreParts: scoreParts,
      };

      const res = await axios.post(
        `http://localhost:8080/api/score/create`,
        payload
      );
      if (res.status == 200) {
        toast.success("Success");
      }
    } catch (error) {
      toast.error("Error");
    }
  };

  return (
    <>
      <div className="col-lg-12">
        <form onSubmit={handleSubmit}>
          <div className="card">
            <div className="card-header">
              <h1>Edit Student Score</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Student Id</label>
                    <input value={id} disabled className="form-control"></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Subject</label>
                    <select
                      value={subjectId}
                      onChange={(e) => setSubjectId(e.target.value)}
                      className="form-control"
                    >
                      {subjectList?.map((sbj) => (
                        <option value={sbj?.subjectId}>
                          {`${sbj?.subjectCode} - ${sbj?.subjectCode}`}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Semester</label>
                    <select
                      value={semesterId}
                      onChange={(e) => setSemesterId(e.target.value)}
                      className="form-control"
                    >
                      <option value={1}>First Semester</option>
                      <option value={2}>Second Semester</option>
                    </select>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Test 15 score</label>
                    <input
                      value={test15Score}
                      onChange={(e) => setTest15Score(e.target.value)}
                      className="form-control"
                      step="0.01"
                      type="number"
                      min="1"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Test 45 score</label>
                    <input
                      value={test45Score}
                      onChange={(e) => setTest45Score(e.target.value)}
                      className="form-control"
                      step="0.01"
                      type="number"
                      min="1"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Test final score</label>
                    <input
                      value={testFinalScore}
                      onChange={(e) => setTestFinalScore(e.target.value)}
                      className="form-control"
                      step="0.01"
                      type="number"
                      min="1"
                    ></input>
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                Update Score
              </button>
              <Link className="btn btn-danger" to={"/student"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default ScoreDetail;
