import { useEffect, useState, useRef } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage, getRank } from "../../commons/utils";
import * as echarts from "echarts";
import React from "react";
import ReactECharts from "echarts-for-react";

const ClassStatistic = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [classId, setClassId] = useState();
  const [classes, setClasses] = useState();
  const [classData, setClassData] = useState();
  const [graphType, setGraphType] = useState("TABLE");
  const [graphOptions, setGraphOptions] = useState({});
  const [pieChartOptions, setPieChartOptions] = useState({});
  const [rankInfo, setRankInfo] = useState({});

  const onChangeGraphType = (e) => {
    setGraphType(e.target.value);
  };

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    loadClass();
  }, []);

  useEffect(() => {
    const params = {
      classId: classId,
    };
    loadClassData(params);
  }, [classId]);

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

  const loadClassData = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },

      params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/statistic/class",
      config
    );
    setClassData(response?.data?.semesterDtoList);
    setRankInfo(response?.data?.studentRankStatisticDto);
    setGraphData(
      response?.data?.semesterDtoList,
      response?.data?.studentRankStatisticDto
    );
  };

  const setGraphData = (semesterDtoList, studentRankStatisticDto) => {
    if (semesterDtoList == undefined || semesterDtoList == null) {
      setGraphOptions(null);
      return;
    }

    const categories = semesterDtoList?.map(
      (s) => s.firstName + " " + s.lastName
    );

    const firstSemesterData = semesterDtoList?.map((s) => s.firstSemAvgScore);
    const secondSemesterData = semesterDtoList?.map((s) => s.secondSemAvgScore);
    const finalSemesterData = semesterDtoList?.map((s) => s.finalSemAvgScore);

    const options = {
      title: {
        text: "Student score",
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "shadow",
        },
      },
      legend: {},
      grid: {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true,
      },
      xAxis: {
        type: "value",
        boundaryGap: [0, 0.001],
        max: 10,
      },
      yAxis: {
        type: "category",
        data: categories,
      },
      series: [
        {
          name: "1st Sem",
          type: "bar",
          data: firstSemesterData,
        },
        {
          name: "2st Sem",
          type: "bar",
          data: secondSemesterData,
        },
        {
          name: "Final",
          type: "bar",
          data: finalSemesterData,
        },
      ],
    };
    setGraphOptions(options);

    const pieChartOptions = {
      title: {
        text: "Student Rank chart",
        left: "center",
      },
      tooltip: {
        trigger: "item",
      },
      legend: {
        orient: "vertical",
        left: "left",
      },
      series: [
        {
          name: "Number of student",
          type: "pie",
          radius: "50%",
          data: [
            { value: studentRankStatisticDto?.badCount, name: "Bad" },
            { value: studentRankStatisticDto?.avgCount, name: "Average" },
            { value: studentRankStatisticDto?.goodCount, name: "Good" },
            { value: studentRankStatisticDto?.veryGoodCount, name: "VeryGood" },
            {
              value: studentRankStatisticDto?.excellentCount,
              name: "Excellent",
            },
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
        },
      ],
    };
    setPieChartOptions(pieChartOptions);
  };

  return (
    <div className="col-lg-12">
      <form>
        <div className="card">
          <div className="card-header">
            <h1>Class Statistic</h1>
          </div>
          <div className="card-body">
            <div className="row">
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
            </div>
            <div className="row"></div>
          </div>
        </div>
      </form>
      <div className="container" style={{ marginBottom: "30px" }}>
        <div className="row">
          <div className="col-lg-12 text-right">
            <input
              type="radio"
              name="graphType"
              value="TABLE"
              id="TABLE"
              style={{ marginLeft: "20px" }}
              checked={graphType === "TABLE"}
              onChange={onChangeGraphType}
            />
            <label style={{ fontSize: "20px" }}>Table</label>
            <input
              type="radio"
              name="graphType"
              value="CHART"
              id="CHART"
              checked={graphType === "CHART"}
              onChange={onChangeGraphType}
            />
            <label style={{ fontSize: "20px" }}>Chart</label>
          </div>
        </div>
      </div>
      <div style={{ fontSize: "30px" }}>
        Total Student: {rankInfo?.totalStudentCount}
      </div>
      {graphType == "TABLE" ? (
        <>
          <div className="py-4">
            <table className="table border shadow">
              <thead>
                <tr>
                  <th scope="col">Student Id</th>
                  <th scope="col">Name</th>
                  <th scope="col">First Semester Average</th>
                  <th scope="col">Second Semester Average</th>
                  <th scope="col">Final Average</th>
                  <th scope="col">Rank</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {classData?.map((cd, index) => (
                  <>
                    <tr>
                      <th scope="row" key={cd?.studentId}>
                        {cd?.studentId}
                      </th>
                      <td>{cd?.firstName + " " + cd?.lastName}</td>
                      <td>{cd?.firstSemAvgScore}</td>
                      <td>{cd?.secondSemAvgScore}</td>
                      <td>{cd?.finalSemAvgScore}</td>
                      <td>{getRank(cd?.rank)}</td>
                      <td>
                        <Link
                          className="btn btn-success"
                          to={`/student-statistic/${cd?.studentId}`}
                        >
                          View Detail
                        </Link>
                      </td>
                    </tr>
                  </>
                ))}
              </tbody>
            </table>
          </div>
        </>
      ) : (
        <>
          <ReactECharts option={graphOptions} />;
          <ReactECharts option={pieChartOptions} />;
        </>
      )}
    </div>
  );
};

export default ClassStatistic;
