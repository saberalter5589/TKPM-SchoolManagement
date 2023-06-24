export const getUserInfoFromLocalStorage = () => {
  const userInfoStr = localStorage.getItem("user");
  if (userInfoStr != null || userInfoStr !== "") {
    const userInfo = JSON.parse(userInfoStr);
    return {
      userTypeId: userInfo?.userTypeId,
      userId: userInfo?.id,
      userName: userInfo?.username,
      password: userInfo?.password,
    };
  }
  return null;
};

function isoDateWithoutTimeZone(date) {
  if (date == null) return date;
  var timestamp = date.getTime() - date.getTimezoneOffset() * 60000;
  var correctDate = new Date(timestamp);
  // correctDate.setUTCHours(0, 0, 0, 0); // uncomment this if you want to remove the time
  return correctDate.toISOString();
}

export const convertFromDateToString = (src) => {
  return isoDateWithoutTimeZone(src).substring(0, 10);
};

export const convertFromStringToDate = (src) => {
  return new Date(src + "T00:00:00");
};

export const getRank = (rank) => {
  switch (rank) {
    case 0:
      return "BAD";
    case 1:
      return "AVERAGE";
    case 2:
      return "GOOD";
    case 3:
      return "VERY GOOD";
    case 4:
      return "EXCELLENT";
  }
};
