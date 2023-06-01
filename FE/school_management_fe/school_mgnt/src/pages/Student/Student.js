import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const Student = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [students, setStudents] = useState([]);
  const [studentId, setStudentId] = useState();
  const [classId, setClassId] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [gender, setGender] = useState(-1);
  const [address, setAddress] = useState();
  const [email, setEmail] = useState();
  const [classes, setClasses] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    loadClass();
    loadStudent(null);
  }, []);

  const loadStudent = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/student/search",
      config
    );
    setStudents(response?.data?.studentDtoList);
  };

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
    const classList = [
      {
        classId: -1,
        classCode: "All",
        className: "All",
      },
      ...response?.data?.classDtoList,
    ];
    setClasses(classList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      studentId: studentId ? studentId : null,
      classId: classId && classId != -1 ? classId : null,
      firstName: firstName ? firstName : null,
      lastName: lastName ? lastName : null,
      gender: gender && gender != -1 ? gender : null,
      address: address ? address : null,
      email: email ? email : null,
    };
    loadStudent(params);
  };

  const renderGender = (val) => {
    switch (val) {
      case 0:
        return "MALE";
      case 1:
        return "FEMALE";
    }
  };

  const deleteStudent = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/student/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadStudent();
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
            <h1>Student Management</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Student Id</label>
                  <input
                    value={studentId}
                    onChange={(e) => setStudentId(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
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
              <div className="col-lg-6">
                <div className="form-group">
                  <label>First Name</label>
                  <input
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Last Name</label>
                  <input
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Gender</label>
                  <select
                    value={gender}
                    onChange={(e) => setGender(e.target.value)}
                    className="form-control"
                  >
                    <option value={-1}>All</option>
                    <option value={0}>Male</option>
                    <option value={1}>FEMALE</option>
                  </select>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Address</label>
                  <input
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Email</label>
                  <input
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
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
            <Link className="btn btn-success" to={`/student-detail/${-1}`}>
              Create new
            </Link>
          </div>
        </div>
      </form>
      <h2>Student List</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Student Id</th>
              <th scope="col">Class</th>
              <th scope="col">Name</th>
              <th scope="col">Gender</th>
              <th scope="col">Address</th>
              <th scope="col">Email</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {students?.map((std, index) => (
              <>
                <tr>
                  <th scope="row" key={std?.studentId}>
                    {std?.studentId}
                  </th>
                  <td>{std?.classDto?.className}</td>
                  <td>{std?.firstName + " " + std?.lastName}</td>
                  <td>{renderGender(std?.gender)}</td>
                  <td>{std?.address}</td>
                  <td>{std?.email}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/student-detail/${std?.studentId}`}
                    >
                      Edit
                    </Link>
                    <Link
                      className="btn btn-info"
                      to={`/score-detail/${std?.studentId}`}
                    >
                      Edit Score
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) => deleteStudent(e, std?.studentId)}
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

export default Student;
