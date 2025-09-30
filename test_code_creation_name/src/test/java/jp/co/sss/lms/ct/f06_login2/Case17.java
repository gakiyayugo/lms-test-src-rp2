package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		goTo("http://localhost:8080/lms");

		assertEquals("ログイン | LMS", getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() throws InterruptedException {

		final WebElement id = getElementByName("loginId");
		id.clear();
		id.sendKeys("StudentAA05");

		final WebElement pass = getElementByName("password");
		pass.clear();
		pass.sendKeys("StudentAA05");
		pass.sendKeys(Keys.ENTER);

		getEvidence(new Object() {
		});

		Thread.sleep(5);
		assertEquals("セキュリティ規約 | LMS", getTitle());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {

		visibilityTimeout(By.cssSelector("input[type='checkbox']"), 5);
		final WebElement check = getElementBycssSelector("input[type='checkbox']");
		check.click();

		final WebElement next = getElementBycssSelector("button[type='submit']");
		next.click();

		getEvidence(new Object() {
		});

		assertEquals("パスワード変更 | LMS", getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {

		visibilityTimeout(By.className("form-control"), 5);
		final List<WebElement> form = getElementsByListClassName("form-control");
		form.get(0).sendKeys("StudentAA05");
		form.get(1).sendKeys("Student05");
		form.get(2).sendKeys("Student05");

		scrollBy("100");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement fix = getElementBycssSelector("button[type='submit']");
		fix.click();

		visibilityTimeout(By.id("upd-btn"), 5);
		final WebElement update = getElementById("upd-btn");
		update.click();

		getEvidence(new Object() {
		});

		assertEquals("コース詳細 | LMS", getTitle());

	}

}
