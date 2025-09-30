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
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
		id.sendKeys("StudentAA04");

		final WebElement pass = getElementByName("password");
		pass.clear();
		pass.sendKeys("StudentAA04");
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
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement fix = getElementBycssSelector("button[type='submit']");
		fix.click();

		visibilityTimeout(By.id("upd-btn"), 5);
		final WebElement update = getElementById("upd-btn");
		update.click();

		getEvidence(new Object() {
		});

		visibilityTimeout(By.className("error"), 5);
		final List<WebElement> error = getElementsByListClassName("error");

		assertEquals("現在のパスワードは必須です。", error.get(1).getText());

		String pattern1 = error.get(2).getText();
		char pattern2 = '「';

		if (pattern1.charAt(0) == pattern2) {
			assertEquals("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。\n"
					+ "パスワードは必須です。", error.get(2).getText());
		} else {
			assertEquals("パスワードは必須です。\n「パスワード」には半角英数字のみ使用可能です。"
					+ "また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。", error.get(2).getText());
		}

		assertEquals("確認パスワードは必須です。", error.get(3).getText());

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() throws InterruptedException {

		visibilityTimeout(By.className("form-control"), 5);
		final List<WebElement> form = getElementsByListClassName("form-control");
		form.get(0).sendKeys("StudentAA04");
		form.get(1).sendKeys("1234567890Abcdefghijk");
		form.get(2).sendKeys("1234567890Abcdefghijk");

		scrollBy("100");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement fix = getElementBycssSelector("button[type='submit']");
		fix.click();

		visibilityTimeout(By.id("upd-btn"), 5);
		final WebElement update = getElementById("upd-btn");
		update.click();

		visibilityTimeout(By.className("error"), 5);
		final List<WebElement> error = getElementsByListClassName("error");

		Thread.sleep(5);
		assertEquals("パスワードの長さが最大値(20)を超えています。", error.get(1).getText());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() throws InterruptedException {

		visibilityTimeout(By.className("form-control"), 5);
		final List<WebElement> form = getElementsByListClassName("form-control");
		form.get(0).sendKeys("StudentAA04");
		form.get(1).sendKeys("1234567890");
		form.get(2).sendKeys("1234567890");

		scrollBy("100");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement fix = getElementBycssSelector("button[type='submit']");
		fix.click();

		visibilityTimeout(By.id("upd-btn"), 5);
		final WebElement update = getElementById("upd-btn");
		update.click();

		visibilityTimeout(By.className("error"), 5);
		final List<WebElement> error = getElementsByListClassName("error");

		Thread.sleep(5);
		assertEquals("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。", error.get(1).getText());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() throws InterruptedException {

		visibilityTimeout(By.className("form-control"), 5);
		final List<WebElement> form = getElementsByListClassName("form-control");
		form.get(0).sendKeys("StudentAA04");
		form.get(1).sendKeys("Abc1234567890");
		form.get(2).sendKeys("Aaa1234567890");

		scrollBy("100");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement fix = getElementBycssSelector("button[type='submit']");
		fix.click();

		visibilityTimeout(By.id("upd-btn"), 5);
		final WebElement update = getElementById("upd-btn");
		update.click();

		visibilityTimeout(By.className("error"), 5);
		final List<WebElement> error = getElementsByListClassName("error");

		Thread.sleep(5);
		assertEquals("パスワードと確認パスワードが一致しません。", error.get(1).getText());

		getEvidence(new Object() {
		});

	}

}
