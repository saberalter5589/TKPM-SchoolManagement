import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ADMIN } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const ClassType = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [classTypes, setClassTypes] = useState();
  const [classTypeId, setClassTypeId] = useState();
  const [classIndex, setClassIndex] = useState();
  const [classTypeCode, setClassTypeCode] = useState();
  const [classTypeName, setClassTypeName] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    loadClassType(null);
  }, []);

  const loadClassType = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/class-type/search",
      config
    );
    setClassTypes(response?.data?.classTypeDtoList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      classTypeId: classTypeId ? classTypeId : null,
      classIndex: classIndex ? classIndex : null,
      classTypeCode: classTypeCode ? classTypeCode : null,
      classTypeName: classTypeName ? classTypeName : null,
    };
    loadClassType(params);
  };

  const deleteClassType = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/class-type/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadClassType();
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
            <h1>Search Class Type</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Class Type Id</label>
                  <input
                    value={classTypeId}
                    onChange={(e) => setClassTypeId(e.target.value)}
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
          </div>
          <div className="card-footer text-center">
            <button type="submit" className="btn btn-primary">
              Search
            </button>
            <Link className="btn btn-success" to={`/class-type-detail/${-1}`}>
              Create new
            </Link>
          </div>
        </div>
      </form>
      <h2>Staff List</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Class Type Id</th>
              <th scope="col">Class Index</th>
              <th scope="col">Class Type Code</th>
              <th scope="col">Class Type Name</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {classTypes?.map((classType, index) => (
              <>
                <tr>
                  <th scope="row" key={classType?.classTypeId}>
                    {classType?.classTypeId}
                  </th>
                  <td>{classType?.classIndex}</td>
                  <td>{classType?.classTypeCode}</td>
                  <td>{classType?.classTypeName}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/class-type-detail/${classType?.classTypeId}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) =>
                        deleteClassType(e, classType?.classTypeId)
                      }
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

export default ClassType;
