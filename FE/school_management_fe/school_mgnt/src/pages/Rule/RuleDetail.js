import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ADMIN } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const RuleDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const [minAge, setMinAge] = useState();
  const [maxAge, setMaxAge] = useState();
  const [test15Rate, setTest15Rate] = useState();
  const [test45Rate, setTest45Rate] = useState();
  const [finalExamRate, setFinalExamRate] = useState();
  const [firstSemRate, setFirstSemRate] = useState();
  const [secondSemRate, setSecondSemRate] = useState();
  const navigate = useNavigate();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    loadRule();
  }, []);

  const loadRule = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/rule/search",
      config
    );
    const rule = response?.data?.ruleDto;
    setMinAge(rule?.minAge);
    setMaxAge(rule?.maxAge);
    setTest15Rate(rule?.test15Rate);
    setTest45Rate(rule?.test45Rate);
    setFinalExamRate(rule?.finalExamRate);
    setFirstSemRate(rule?.firstSemRate);
    setSecondSemRate(rule?.secondSemRate);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        minAge: minAge,
        maxAge: maxAge,
        test15Rate: test15Rate,
        test45Rate: test45Rate,
        finalExamRate: finalExamRate,
        firstSemRate: firstSemRate,
        secondSemRate: secondSemRate,
      };
      const res = await axios.put(`http://localhost:8080/api/rule`, payload);
      if (res.status == 200) {
        toast.success("Success");
      }
    } catch (error) {
      toast.error("Error");
    }
  };
  return (
    <div className="col-lg-12">
      <form onSubmit={handleSubmit}>
        <div className="card">
          <div className="card-header">
            <h1>Rule management</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Min Age</label>
                  <input
                    value={minAge}
                    onChange={(e) => setMinAge(e.target.value)}
                    className="form-control"
                    type="number"
                    min="1"
                  />
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Max Age</label>
                  <input
                    value={maxAge}
                    onChange={(e) => setMaxAge(e.target.value)}
                    className="form-control"
                    type="number"
                    min="1"
                  />
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-lg-4">
                <div className="form-group">
                  <label>Test 15 rate</label>
                  <input
                    value={test15Rate}
                    onChange={(e) => setTest15Rate(e.target.value)}
                    className="form-control"
                    type="number"
                    min="1"
                  />
                </div>
              </div>
              <div className="col-lg-4">
                <div className="form-group">
                  <label>Test 45 Rate</label>
                  <input
                    value={test45Rate}
                    onChange={(e) => setTest45Rate(e.target.value)}
                    className="form-control"
                    step="0.01"
                    type="number"
                    min="1"
                  ></input>
                </div>
              </div>
              <div className="col-lg-4">
                <div className="form-group">
                  <label>Final Exam Rate</label>
                  <input
                    value={finalExamRate}
                    onChange={(e) => setFinalExamRate(e.target.value)}
                    className="form-control"
                    step="0.01"
                    type="number"
                    min="1"
                  ></input>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>First Semester Rate</label>
                  <input
                    value={firstSemRate}
                    onChange={(e) => setFirstSemRate(e.target.value)}
                    className="form-control"
                    type="number"
                    min="1"
                  />
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Second Semester Rate</label>
                  <input
                    value={secondSemRate}
                    onChange={(e) => setSecondSemRate(e.target.value)}
                    className="form-control"
                    type="number"
                    min="1"
                  />
                </div>
              </div>
            </div>
          </div>
          <div className="card-footer text-center">
            <button type="submit" className="btn btn-primary">
              Edit
            </button>
            <Link className="btn btn-danger" to={"/subject"}>
              Back
            </Link>
          </div>
        </div>
      </form>
    </div>
  );
};

export default RuleDetail;
