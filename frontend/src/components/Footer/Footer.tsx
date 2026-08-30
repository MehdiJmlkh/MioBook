import "./Footer.css";

const Footer = () => {
  return (
    <footer className="page-footer">
      <span>Copyright &copy; {new Date().getFullYear()} - MioBook</span>
    </footer>
  );
};

export default Footer;
