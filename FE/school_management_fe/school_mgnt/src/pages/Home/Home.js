import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { ADMIN, STAFF } from "../../commons/constant";
const Home = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  useEffect(() => {
    if (userInfo?.userId == undefined) {
      navigate("/login");
    }
  }, []);

  const renderHomeContent = () => {
    switch (userInfo?.userTypeId) {
      case ADMIN:
        return (
          <>
            <h1>This page is for Admin member of school</h1>
            <h4>Admin can manage: Rule, Staff, Class Type, Subject</h4>
          </>
        );

      case STAFF:
        return (
          <>
            <h1>This page is for Staff member of school</h1>
            <h4>
              Staff can manage: Class, Student and generate statistics for
              student score
            </h4>
          </>
        );
    }
  };

  return <div>{renderHomeContent()}</div>;
};

export default Home;
