import { createBrowserRouter, RouterProvider } from "react-router";
import Layout from "./layout/ui/Layout";
import Page404 from "../pages/404/ui/Page404";
import { PathConstants } from "./pathConstants";
import Articles from "../pages/Article/ui/Articles";
import Profile from "../pages/Profile/ui/Profile";
import AuthFormComponent from "../widgets/auth/ui/AuthComponent";
import CreateArticle from "../pages/CreateArticle/ui/CreateArticle";



function App() {
  const router = createBrowserRouter([
    {
      element: <Layout />,
      errorElement: <Page404 />,
      children: [
        {
          path: PathConstants.HOME,
          element: <AuthFormComponent />,
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
        }
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App