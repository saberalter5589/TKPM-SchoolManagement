import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import {
  convertFromStringToDate,
  convertFromDateToString,
} from "../../commons/utils";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const StudentDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [studentId, setStudentId] = useState();
  const [classId, setClassId] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [birthDate, setBirthDate] = useState();
  const [gender, setGender] = useState(0);
  const [address, setAddress] = useState();
  const [email, setEmail] = useState();
  const [description, setDescription] = useState();
  const [note, setNote] = useState();
  const [classes, setClasses] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    loadClass();
    if (id != -1) {
      const params = {
        studentId: id,
      };
      loadStudent(params);
    }
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
    const student = response?.data?.studentDtoList[0];
    setStudentId(student?.studentId);
    setClassId(student?.classDto?.classId);
    setFirstName(student?.firstName);
    setLastName(student?.lastName);
    setBirthDate(
      student?.birthDate ? convertFromStringToDate(student?.birthDate) : null
    );
    setGender(student?.gender);
    setAddress(student?.address);
    setEmail(student?.email);
    setDescription(student?.description);
    setNote(student?.note);
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
    setClasses(response?.data?.classDtoList);
    setClassId(response?.data?.classDtoList[0]?.classId);
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
        firstName: firstName,
        lastName: lastName,
        birthDate: birthDate ? convertFromDateToString(birthDate) : "",
        gender: gender,
        address: address ? address : null,
        email: email ? email : null,
        description: description,
        note: note,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/student/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/student");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/student/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/student");
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
              <h1>{id == -1 ? "Create Student" : "Edit Student"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Student Id</label>
                    <input
                      value={studentId}
                      disabled
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
                    <label>Birth Date</label>
                    <DatePicker
                      selected={birthDate}
                      onChange={(date) => setBirthDate(date)}
                      dateFormat="yyyy-MM-dd"
                      className="form-control"
                    />
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

export default StudentDetail;
