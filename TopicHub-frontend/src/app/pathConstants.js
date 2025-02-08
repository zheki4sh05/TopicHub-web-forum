const BACKEND_HOST = import.meta.env.VITE_APP_BACKEND_ADDRESS
export const PathConstants = {
    HOME: "/",
    LOGIN:"/login",
    ARTICLE: "/articles",
    CREATE_ARTICLE:"/article/create",
    PROFILE: "/profile",
    VIEW:"/articles/view",
    MANAGE_ARTICLES:"/admin/articles",
    MANAGE_USER:"/admin/profile",
    MANAGE_COMMENTS:"/admin/comments",
    MANAGE_HUBS:"/admin/hubs",
    PROFILE_ARTICLE:"/profile/article",
    SEARCH:"/search",
    EDIT:"/article/edit",
    ADMIN_PANEL:"http://"+BACKEND_HOST+"/auth"
}
