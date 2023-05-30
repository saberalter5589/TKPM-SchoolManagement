import logo from "./logo.svg";
import "./App.css";
import { getUserInfoFromLocalStorage } from "./commons/utils";
import { ToastContainer } from "react-toastify";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Login from "./pages/Login/Login";
import { useEffect, useState } from "react";
import Home from "./pages/Home/Home";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Header from "./components/Header";
import Staff from "./pages/Staff/Staff";
import StaffDetail from "./pages/Staff/StaffDetail";
import ClassType from "./pages/ClassType/ClassType";
import ClassTypeDetail from "./pages/ClassType/ClassTypeDetail";
import Subject from "./pages/Subject/Subject";
import SubjectDetail from "./pages/Subject/SubjectDetail";
import RuleDetail from "./pages/Rule/RuleDetail";
import Class from "./pages/Class/Class";
import ClassDetail from "./pages/Class/ClassDetail";

function App() {
  const [isLogin, setIsLogin] = useState(true);
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());

  return (
    <div className="App">
      <ToastContainer theme="colored"></ToastContainer>
      <BrowserRouter>
        <Header isLogin={isLogin} userInfo={userInfo} />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route
            path="/login"
            element={
              <Login setIsLogin={setIsLogin} setUserInfo={setUserInfo} />
            }
          />
          <Route path="/staff" element={<Staff />} />
          <Route path="/staff-detail/:id" element={<StaffDetail />} />
          <Route path="/class-type" element={<ClassType />} />
          <Route path="/class-type-detail/:id" element={<ClassTypeDetail />} />
          <Route path="/subject" element={<Subject />} />
          <Route path="/subject-detail/:id" element={<SubjectDetail />} />
          <Route path="/rule" element={<RuleDetail />} />
          <Route path="/class" element={<Class />} />
          <Route path="/class-detail/:id" element={<ClassDetail />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
