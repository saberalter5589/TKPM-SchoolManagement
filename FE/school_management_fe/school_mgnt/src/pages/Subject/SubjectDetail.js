import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { ADMIN } from "../../commons/constant";

const SubjectDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [subjectId, setSubjectId] = useState();
  const [subjectCode, setSubjectCode] = useState();
  const [subjectName, setSubjectName] = useState();
  const [avgScore, setAvgScore] = useState(5);
  const [description, setDescription] = useState();
  const [note, setNote] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    if (id != -1) {
      const params = {
        subjectId: id,
      };
      loadSubject(params);
    }
  }, []);

  const loadSubject = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/subject/search",
      config
    );
    const subject = response?.data?.subjectDtoList[0];
    setSubjectId(subject?.subjectId);
    setSubjectCode(subject?.subjectCode);
    setSubjectName(subject?.subjectName);
    setDescription(subject?.description);
    setAvgScore(subject?.avgScore);
    setNote(subject?.note);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        subjectCode: subjectCode,
        subjectName: subjectName,
        avgScore: avgScore,
        description: description,
        note: note,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/subject/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/subject");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/subject/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/subject");
        }
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
              <h1>{id == -1 ? "Create Class Type" : "Edit Class Type"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Subject Id</label>
                    <input
                      value={subjectId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Subject Code</label>
                    <input
                      value={subjectCode}
                      onChange={(e) => setSubjectCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Subject Name</label>
                    <input
                      value={subjectName}
                      onChange={(e) => setSubjectName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Average Score</label>
                    <input
                      value={avgScore}
                      onChange={(e) => setAvgScore(e.target.value)}
                      className="form-control"
                      step="0.01"
                      type="number"
                      min="1"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Description</label>
                    <textarea
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                      className="form-control"
                    ></textarea>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Note</label>
                    <textarea
                      value={note}
                      onChange={(e) => setNote(e.target.value)}
                      className="form-control"
                    ></textarea>
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                {id == -1 ? "Create" : "Edit"}
              </button>
              <Link className="btn btn-danger" to={"/subject"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default SubjectDetail;
