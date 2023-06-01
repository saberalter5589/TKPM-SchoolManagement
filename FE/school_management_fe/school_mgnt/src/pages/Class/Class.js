import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const Class = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [classes, setClasses] = useState();
  const [classTypes, setClassTypes] = useState();
  const [classId, setClassId] = useState();
  const [classTypeId, setClassTypeId] = useState();
  const [classCode, setClassCode] = useState();
  const [className, setClassName] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    loadClassType(null);
    loadClass(null);
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
    const classTypeList = [
      {
        classTypeId: -1,
        classTypeCode: "All",
        classTypeName: "All",
      },
      ...response?.data?.classTypeDtoList,
    ];
    setClassTypes(classTypeList);
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
    setClasses(response?.data?.classDtoList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      classId: classId ? classId : null,
      classTypeId: classTypeId && classTypeId != -1 ? classTypeId : null,
      classCode: classCode ? classCode : null,
      className: className ? className : null,
    };
    loadClass(params);
  };

  const deleteClass = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/class/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadClass();
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
            <h1>Class management</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Class Id</label>
                  <input
                    value={classId}
                    onChange={(e) => setClassId(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Class Code</label>
                  <input
                    value={classCode}
                    onChange={(e) => setClassCode(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Class Name</label>
                  <input
                    value={className}
                    onChange={(e) => setClassName(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
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
            </div>
          </div>
          <div className="card-footer text-center">
            <button type="submit" className="btn btn-primary">
              Search
            </button>
            <Link className="btn btn-success" to={`/class-detail/${-1}`}>
              Create new
            </Link>
          </div>
        </div>
      </form>
      <h2>Class List</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Class Id</th>
              <th scope="col">Class Code</th>
              <th scope="col">Class Name</th>
              <th scope="col">Class Type Name</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {classes?.map((curClass, index) => (
              <>
                <tr>
                  <th scope="row" key={curClass?.classId}>
                    {curClass?.classId}
                  </th>
                  <td>{curClass?.classCode}</td>
                  <td>{curClass?.className}</td>
                  <td>{curClass?.classTypeDto?.classTypeName}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/class-detail/${curClass?.classId}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) => deleteClass(e, curClass?.classId)}
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

export default Class;
