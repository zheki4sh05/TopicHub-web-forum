import { createBrowserRouter, RouterProvider } from "react-router";
import Layout from "./layout/ui/Layout";
import Page404 from "../pages/404/ui/Page404";
import { PathConstants } from "./pathConstants";
import Articles from "../pages/Article/ui/Articles";
import Profile from "../pages/Profile/ui/Profile";
import ArticleView from "../pages/ArticleView/ui/ArticleView";
import ManageArticle from "../pages/ManagerArticle/ui/ManageArticle";
import Login from "../pages/Login/ui/Login";
import ProfileArticle from "../pages/Profile/ui/ProfileArticle";
import SearchPage from "../pages/SearchPage/ui/SearchPage";
import ManageAuthor from "../pages/ManageAuthor/ui/ManageAuthor";
import ManageComments from './../pages/ManageComments/api/ManageComments';
import ManageHubs from "../pages/ManageHubs/ui/ManageHubs";
import CreateArticle from "../pages/Sandbox/ui/CreateArticle";
import EditArticle from "../pages/Sandbox/ui/EditArticle";



function App() {
  const router = createBrowserRouter([
    {
      element: <Layout />,
      errorElement: <Page404 />,
    
      children: [
        {
          path: PathConstants.HOME,
          element: <Articles />,
        },
        {
          path: PathConstants.ARTICLE,
          element: <Articles />,
        },
        {
          path: PathConstants.CREATE_ARTICLE,
          element: <CreateArticle />,
        },
        {
          path: PathConstants.PROFILE,
          element: <Profile />,
        },
        {
          path: PathConstants.VIEW,
          element: <ArticleView />,
        },
        {
          path: PathConstants.MANAGE_ARTICLES,
          element: <ManageArticle />,
        },
        {
          path: PathConstants.MANAGE_USER,
          element: <ManageAuthor />,
        },
        {
          path: PathConstants.MANAGE_COMMENTS,
          element: <ManageComments />,
        },
        {
          path: PathConstants.MANAGE_HUBS,
          element: <ManageHubs />,
        },
        {
          path:PathConstants.LOGIN,
          element:<Login />,
        },
        {
          path:PathConstants.PROFILE_ARTICLE,
          element:<ProfileArticle />,
        },
        {
          path:PathConstants.SEARCH,
          element:<SearchPage />,
        },
        {
          path:PathConstants.EDIT,
          element:<EditArticle/>,
        },
        {
          path: "*",
          element: <Page404 />,
        },

     
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App