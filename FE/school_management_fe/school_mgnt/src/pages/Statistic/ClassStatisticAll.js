import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { STAFF } from "../../commons/constant";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage, getRank } from "../../commons/utils";
import React from "react";
import ReactECharts from "echarts-for-react";

const ClassStatisticAll = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const [graphOptions, setGraphOptions] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != STAFF) {
      navigate("/login");
    }
    loadClass();
  }, []);

  const loadClass = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/statistic/class/all",
      config
    );
    setGraphData(response?.data?.classRankDtoList);
  };

  const setGraphData = (classRankDtoList) => {
    if (classRankDtoList == undefined) {
      setGraphOptions(null);
      return;
    }
    const categories = classRankDtoList?.map((s) => s?.className);

    const badData = classRankDtoList?.map((c) => c?.badCount);
    const avgData = classRankDtoList?.map((c) => c?.avgCount);
    const goodData = classRankDtoList?.map((c) => c?.goodCount);
    const veryGoodCount = classRankDtoList?.map((c) => c?.veryGoodCount);
    const excellentCount = classRankDtoList?.map((c) => c?.excellentCount);

    const options = {
      title: {
        text: "Class Rank",
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
          name: "Excellent",
          type: "bar",
          data: excellentCount,
        },
        {
          name: "Very Good",
          type: "bar",
          data: veryGoodCount,
        },
        {
          name: "Good",
          type: "bar",
          data: goodData,
        },
        {
          name: "Average",
          type: "bar",
          data: avgData,
        },
        {
          name: "Bad",
          type: "bar",
          data: badData,
        },
      ],
    };
    setGraphOptions(options);
  };

  return (
    <div className="col-lg-12">
      <form>
        <div className="card">
          <div className="card-header">
            <h1>Class Statistic</h1>
          </div>
          <div className="card-body"></div>
        </div>
      </form>
      <div className="container" style={{ marginBottom: "30px" }}>
        <div className="row"></div>
      </div>
      <div style={{ fontSize: "30px" }}>
        <ReactECharts option={graphOptions} />
      </div>
    </div>
  );
};

export default ClassStatisticAll;
