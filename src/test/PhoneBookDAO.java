package test;

import java.util.List;

public interface PhoneBookDAO {
	public boolean createSeq();
	public boolean createTable();	// 테이블 생성
	public List<PhoneBookVO> getList();	//	전체 목록
	public List<PhoneBookVO> search(String name);	//	PK로 객체 찾기
	public boolean insert(PhoneBookVO phoneBookVo);	//	레코드 삽임
	public boolean delete(Long id);	//	PK로 객체 삭제
	public boolean tableDrop();	//	테이블 삭제
	public boolean seqDrop();
}
