import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const ClassDetail = () => {
  const { id } = useParams();
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [classTypes, setClassTypes] = useState([]);
  const [classId, setClassId] = useState();
  const [classTypeId, setClassTypeId] = useState();
  const [classCode, setClassCode] = useState();
  const [className, setClassName] = useState();
  const [maxStudentNum, setMaxStudentNum] = useState();
  const [description, setDescription] = useState();
  const [note, setNote] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    loadClassType(null);
    if (id != -1) {
      const params = {
        classId: id,
      };
      loadClass(params);
    }
  }, []);

  const loadClassType = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/class-type/search",
      config
    );
    setClassTypes(response?.data?.classTypeDtoList);
    setClassTypeId(response?.data?.classTypeDtoList[0]?.classTypeId);
  };

  const loadClass = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/class/search",
      config
    );
    const curClass = response?.data?.classDtoList[0];
    setClassId(curClass?.classId);
    setClassCode(curClass?.classCode);
    setClassName(curClass?.className);
    setClassTypeId(curClass?.classTypeDto?.classTypeId);
    setMaxStudentNum(curClass?.maxStudentNum);
    setDescription(curClass?.description);
    setNote(curClass?.note);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        classId: classId,
        classCode: classCode,
        className: className,
        maxStudentNum: maxStudentNum,
        classTypeId: classTypeId,
        description: description,
        note: note,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/class/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/class");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/class/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/class");
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
              <h1>{id == -1 ? "Create Class" : "Edit Class"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Class Id</label>
                    <input
                      value={classId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Class Code</label>
                    <input
                      value={classCode}
                      onChange={(e) => setClassCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Class Name</label>
                    <input
                      value={className}
                      onChange={(e) => setClassName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Class Type</label>
                    <select
                      value={classTypeId}
                      onChange={(e) => setClassTypeId(e.target.value)}
                      className="form-control"
                    >
                      {classTypes?.map((ct) => (
                        <option value={ct?.classTypeId}>
                          {`${ct?.classTypeCode} - ${ct?.classTypeName}`}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Max Student Number</label>
                    <input
                      value={maxStudentNum}
                      onChange={(e) => setMaxStudentNum(e.target.value)}
                      className="form-control"
                      type="number"
                      min="1"
                    />
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
              <Link className="btn btn-danger" to={"/class"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default ClassDetail;
