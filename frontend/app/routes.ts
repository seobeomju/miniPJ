import {type RouteConfig, index, route} from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("/todo", "layout/todoLayout.tsx", [
        route("list",'routes/todo/listPage.tsx'),
        route("add",'routes/todo/addPage.tsx'),
        route("read/:tno",'routes/todo/readPage.tsx'),
        route("modify/:tno","routes/todo/modifyPage.tsx"),
    ]),
    route('/member/login','routes/member/loginPage.tsx'),
    route('/member/kakao', 'routes/member/kakaoRedirect.tsx'),
    route('/member/join', 'routes/member/joinPage.tsx'),
] satisfies RouteConfig;
