import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { ADMIN } from "../../commons/constant";

const StaffDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [staffId, setStaffId] = useState();
  const [userName, setUserName] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    if (id != -1) {
      const params = {
        staffId: id,
      };
      loadStaff(params);
    }
  }, []);

  const loadStaff = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };

    try {
      const response = await axios.get(
        "http://localhost:8080/api/staff/search",
        config
      );
      const staff = response?.data?.userDtoList[0];
      setStaffId(id);
      setUserName(staff?.userName);
      setPassword(staff?.password);
      setFirstName(staff?.firstName);
      setLastName(staff?.lastName);
      setEmail(staff?.email);
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
        userName: userName,
        password: password,
        firstName: firstName,
        lastName: lastName,
        email: email,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/staff/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/staff");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/staff/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/staff");
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
              <h1>{id == -1 ? "Create Staff" : "Edit Staff"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Staff Id</label>
                    <input
                      value={staffId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>UserName</label>
                    <input
                      value={userName}
                      onChange={(e) => setUserName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>Password</label>
                    <input
                      value={password}
                      type="password"
                      onChange={(e) => setPassword(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>FirstName</label>
                    <input
                      value={firstName}
                      onChange={(e) => setFirstName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
                  <div className="form-group">
                    <label>LastName</label>
                    <input
                      value={lastName}
                      onChange={(e) => setLastName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-4">
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
                {id == -1 ? "Create" : "Edit"}
              </button>
              <Link className="btn btn-danger" to={"/staff"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default StaffDetail;
