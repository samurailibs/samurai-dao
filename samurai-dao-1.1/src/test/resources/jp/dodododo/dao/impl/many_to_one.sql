SELECT
	  EMPNO
	, ENAME
	, JOB
	, MGR
	, HIREDATE
	, SAL
	, COMM
	, EMP.DEPTNO
	, TSTAMP
	, DNAME
	, LOC
	, VERSIONNO
FROM
	  EMP
	, DEPT
WHERE
	EMP.DEPTNO = DEPT.DEPTNO


