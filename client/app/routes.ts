import { 
    type RouteConfig, 
    route,
    index,
} from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("login", "./routes/login.tsx"),
    route("logout", "./routes/logout.tsx"),
    route("register", "./routes/register.tsx"),
    route("cards/:cardId", "./routes/cardView.tsx")
] satisfies RouteConfig;
