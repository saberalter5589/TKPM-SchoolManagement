import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { ADMIN } from "../../commons/constant";

const ClassTypeDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [classTypeId, setClassTypeId] = useState();
  const [classIndex, setClassIndex] = useState();
  const [classTypeCode, setClassTypeCode] = useState();
  const [classTypeName, setClassTypeName] = useState();
  const [description, setDescription] = useState();
  const [note, setNote] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    if (id != -1) {
      const params = {
        classTypeId: id,
      };
      loaddClassType(params);
    }
  }, []);

  const loaddClassType = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };

    try {
      const response = await axios.get(
        "http://localhost:8080/api/class-type/search",
        config
      );
      const classType = response?.data?.classTypeDtoList[0];
      setClassTypeId(classType?.classTypeId);
      setClassIndex(classType?.classIndex);
      setClassTypeCode(classType?.classTypeCode);
      setClassTypeName(classType?.classTypeName);
      setDescription(classType?.description);
      setNote(classType?.note);
    } catch (error) {
      toast.error("error");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        classIndex: classIndex,
        classTypeCode: classTypeCode,
        classTypeName: classTypeName,
        description: description,
        note: note,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/class-type/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/class-type");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/class-type/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/class-type");
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
                    <label>Class Type Id</label>
                    <input
                      value={classTypeId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Class Index</label>
                    <input
                      value={classIndex}
                      onChange={(e) => setClassIndex(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Class Type Code</label>
                    <input
                      value={classTypeCode}
                      onChange={(e) => setClassTypeCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Class Type Name</label>
                    <input
                      value={classTypeName}
                      onChange={(e) => setClassTypeName(e.target.value)}
                      className="form-control"
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
              <Link className="btn btn-danger" to={"/class-type"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default ClassTypeDetail;
