import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ADMIN } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const Subject = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [subjects, setSubjects] = useState();
  const [subjectId, setSubjectId] = useState();
  const [subjectCode, setSubjectCode] = useState();
  const [subjectName, setSubjectName] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    loadSubject(null);
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
    setSubjects(response?.data?.subjectDtoList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      subjectId: subjectId ? subjectId : null,
      subjectCode: subjectCode ? subjectCode : null,
      subjectName: subjectName ? subjectName : null,
    };
    loadSubject(params);
  };

  const deleteSubject = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/subject/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadSubject();
      }
    } catch (error) {
      toast.error("Error");
    }
  };

  return (
    <div className="col-lg-12">
      <form onSubmit={handleSearch}>
        <div className="card">
          <div className="card-header">
            <h1>Search Subject</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-4">
                <div className="form-group">
                  <label>Subject Id</label>
                  <input
                    value={subjectId}
                    onChange={(e) => setSubjectId(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-4">
                <div className="form-group">
                  <label>Subject Code</label>
                  <input
                    value={subjectCode}
                    onChange={(e) => setSubjectCode(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-4">
                <div className="form-group">
                  <label>Subject Name</label>
                  <input
                    value={subjectName}
                    onChange={(e) => setSubjectName(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
            </div>
          </div>
          <div className="card-footer text-center">
            <button type="submit" className="btn btn-primary">
              Search
            </button>
            <Link className="btn btn-success" to={`/subject-detail/${-1}`}>
              Create new
            </Link>
          </div>
        </div>
      </form>
      <h2>Subject List</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Subject Id</th>
              <th scope="col">Subject Code</th>
              <th scope="col">Subject Name</th>
              <th scope="col">Average Score</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {subjects?.map((subject, index) => (
              <>
                <tr>
                  <th scope="row" key={subject?.subjectId}>
                    {subject?.subjectId}
                  </th>
                  <td>{subject?.subjectCode}</td>
                  <td>{subject?.subjectName}</td>
                  <td>{subject?.avgScore}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/subject-detail/${subject?.subjectId}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) => deleteSubject(e, subject?.subjectId)}
                    >
                      Delete
                    </button>
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
export default Subject;
