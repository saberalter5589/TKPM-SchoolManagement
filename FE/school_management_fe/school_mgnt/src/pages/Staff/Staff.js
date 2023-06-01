import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ADMIN } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const Staff = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [staffs, setStaffs] = useState([]);
  const [staffId, setStaffId] = useState();
  const [userName, setUserName] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [email, setEmail] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    loadStaff(null);
  }, []);

  const loadStaff = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/staff/search",
      config
    );
    setStaffs(response?.data?.userDtoList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      staffId: staffId ? staffId : null,
      userName: userName ? userName : null,
      firstName: firstName ? firstName : null,
      lastName: lastName ? lastName : null,
      email: email ? email : null,
    };
    loadStaff(params);
  };

  const deleteStaff = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/staff/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadStaff();
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
            <h1>Staff Management</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Staff Id</label>
                  <input
                    value={staffId}
                    onChange={(e) => setStaffId(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Username</label>
                  <input
                    value={userName}
                    onChange={(e) => setUserName(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
            </div>
            <div className="row">
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
            <Link className="btn btn-success" to={`/staff-detail/${-1}`}>
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
              <th scope="col">Staff Id</th>
              <th scope="col">Username</th>
              <th scope="col">First name</th>
              <th scope="col">LastName</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {staffs?.map((staff, index) => (
              <>
                <tr>
                  <th scope="row" key={staff?.userId}>
                    {staff?.userId}
                  </th>
                  <td>{staff?.userName}</td>
                  <td>{staff?.firstName}</td>
                  <td>{staff?.lastName}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/staff-detail/${staff?.userId}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) => deleteStaff(e, staff?.userId)}
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

export default Staff;
