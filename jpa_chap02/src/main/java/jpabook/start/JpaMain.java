package jpabook.start;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
	
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			login(em);
//			chap03(em);
//			testDetached(em);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}

	private static void testDetached(EntityManager em) {
		Member member = new Member();
		
		member.setId("memberA");
		member.setUsername("회원A");
		
		em.persist(member);
		em.detach(member);
		
		System.out.println(em.find(Member.class, "memberA"));
		em.remove(em.find(Member.class, "memberA"));
		
	}

	private static void login(EntityManager em) {
		String id = "id1";
		Member member = new Member();
		member.setId(id);
		member.setUsername("지한");
		member.setAge(2);

		em.persist(member);

		member.setAge(20);

		Member findMember = em.find(Member.class, id);
		System.out.println("findMember = " + findMember.getUsername()
				+ ", age=" + findMember.getAge());

		List<Member> members = em.createQuery("select m from Member m",
				Member.class).getResultList();
		System.out.println("members.size = " + members.size());
		
		em.remove(members);

	}
	
	private static void chap03(EntityManager em){
		
		Member m1 = new Member();
		m1.setId("1");
		m1.setUsername("한재규");
		m1.setAge(35);
		em.persist(m1);
		
		Member find = em.find(Member.class, "1");
		Member find2 = em.find(Member.class, "1");
		em.remove(find);
		System.out.println(find);
		
		System.out.println(find == find2);
		Member find1 = em.find(Member.class, "1");
		System.out.println(find1);
	}
}
