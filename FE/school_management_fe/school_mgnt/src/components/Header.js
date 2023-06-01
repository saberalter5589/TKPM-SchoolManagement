import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ADMIN, STAFF } from "../commons/constant";

const Header = (props) => {
  const { userInfo } = props;
  const [menuList, setMenuList] = useState([]);
  const [title, setTitle] = useState("");
  const renderMenu = () => {
    switch (userInfo?.userTypeId) {
      case ADMIN:
        setMenuList([
          {
            to: "/",
            name: "Home",
            class: "nav-link active",
          },
          {
            to: "/rule",
            name: "Rule",
            class: "nav-link",
          },
          {
            to: "/staff",
            name: "Staff",
            class: "nav-link",
          },
          {
            to: "/class-type",
            name: "Class Type",
            class: "nav-link",
          },
          {
            to: "/subject",
            name: "Subject",
            class: "nav-link",
          },
          {
            to: "/login",
            name: "Logout",
            class: "nav-link",
          },
        ]);
        break;
      case STAFF:
        setMenuList([
          {
            to: "/",
            name: "Home",
            class: "nav-link",
          },
          {
            to: "/class",
            name: "Class",
            class: "nav-link",
          },
          {
            to: "/student",
            name: "Student",
            class: "nav-link",
          },
          {
            to: "/class-statistic",
            name: "Statistic",
            class: "nav-link",
          },
          {
            to: "/login",
            name: "Logout",
            class: "nav-link",
          },
        ]);
        break;
    }
  };

  const renderTitle = () => {
    switch (userInfo?.userTypeId) {
      case ADMIN:
        setTitle("Administrator Page");
        break;
      case STAFF:
        setTitle("Staff Page");
        break;
    }
  };

  useEffect(() => {
    renderMenu();
    renderTitle();
  }, [userInfo]);

  const onChangeActiveTab = (to) => {
    const newMenu = menuList?.map((m) => ({
      ...m,
      class: m?.to == to ? "nav-link active" : "nav-link",
    }));
    setMenuList(newMenu);
  };

  return (
    <div>
      {props?.isLogin && (
        <nav
          class="navbar navbar-expand-lg navbar-light"
          style={{ backgroundColor: "#e3f2fd" }}
        >
          <a class="navbar-brand" href="#">
            {title}
          </a>
          <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNavDropdown"
            aria-controls="navbarNavDropdown"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="nav nav-pills">
              {menuList?.map((menu) => (
                <li
                  class="nav-item"
                  onClick={() => onChangeActiveTab(menu?.to)}
                >
                  <Link class={menu?.class} to={menu?.to}>
                    {menu?.name}
                  </Link>
                </li>
              ))}
            </ul>
          </div>
        </nav>
      )}
    </div>
  );
};

export default Header;
