import "./BorrowedBadge.css";

interface Props {
  expiredDate: string;
}

const BorrowedBadge = ({ expiredDate }: Props) => {
  return (
    <span>
      <div>Borrowed</div>
      <div className="borrowed-badge__date">Until {expiredDate}</div>
    </span>
  );
};

export default BorrowedBadge;
