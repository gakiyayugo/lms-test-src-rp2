package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		final WebElement id = getElementByName("loginId");
		id.clear();
		id.sendKeys("StudentAA02");

		final WebElement pass = getElementByName("password");
		pass.clear();
		pass.sendKeys("Student02");
		pass.sendKeys(Keys.ENTER);

		getEvidence(new Object() {
		});

		assertEquals("コース詳細 | LMS", getTitle());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		final WebElement user = getElementText("ようこそ受講生ＡＡ２さん");
		user.click();

		getEvidence(new Object() {
		});

		assertEquals("ユーザー詳細", getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		scrollBy("250");

		final List<WebElement> fix = getElementsBycssSelector("input[value='修正する']");
		fix.get(0).click();

		pageLoadTimeout(5);
		getEvidence(new Object() {
		});

		assertEquals("レポート登録 | LMS", getTitle());

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		final List<WebElement> text = getElementsByListClassName("form-control");
		text.get(0).clear();

		scrollBy("400");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement submit = getElementBycssSelector("button[type='submit']");
		submit.click();

		final WebElement error = getElementByclassName("errorInput");
		String errorcheck = "";

		if (error.isDisplayed()) {
			errorcheck = "true";
		}

		assertEquals("true", errorcheck);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		final List<WebElement> text = getElementsByListClassName("form-control");
		text.get(0).clear();
		text.get(0).sendKeys("java");

		text.get(1).click();
		final List<WebElement> understand = getElementsByTagName("option");
		understand.get(0).click();

		scrollBy("400");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement submit = getElementBycssSelector("button[type='submit']");
		submit.click();

		final WebElement error = getElementByclassName("errorInput");
		String errorcheck = "";

		if (error.isDisplayed()) {
			errorcheck = "true";
		}

		assertEquals("true", errorcheck);

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() throws InterruptedException {

		final List<WebElement> text = getElementsByListClassName("form-control");
		text.get(1).click();

		visibilityTimeout(By.tagName("option"), 5);
		final List<WebElement> understand = getElementsByTagName("option");
		understand.get(3).click();

		Thread.sleep(5);
		text.get(2).clear();
		text.get(2).sendKeys("A");

		scrollBy("400");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement submit = getElementBycssSelector("button[type='submit']");
		submit.click();

		visibilityTimeout(By.className("errorInput"), 5);
		final WebElement error = getElementByclassName("errorInput");
		String errorcheck = "";

		if (error.isDisplayed()) {
			errorcheck = "true";
		}

		assertEquals("true", errorcheck);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		final List<WebElement> text = getElementsByListClassName("form-control");
		text.get(2).clear();
		text.get(2).sendKeys("11");

		scrollBy("400");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement submit = getElementBycssSelector("button[type='submit']");
		submit.click();

		visibilityTimeout(By.className("errorInput"), 5);
		final WebElement error = getElementByclassName("errorInput");
		String errorcheck = "";

		if (error.isDisplayed()) {
			errorcheck = "true";
		}

		assertEquals("true", errorcheck);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() throws InterruptedException {

		final List<WebElement> text = getElementsByListClassName("form-control");
		text.get(2).clear();
		text.get(3).clear();

		scrollBy("400");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement submit = getElementBycssSelector("button[type='submit']");
		submit.click();

		Thread.sleep(5);
		scrollBy("300");

		visibilityTimeout(By.className("errorInput"), 5);
		final List<WebElement> error = getElementsByListClassName("errorInput");

		String errorcheck = "";

		if (error.get(0).isDisplayed()) {
			errorcheck = "true";
		}

		assertEquals("true", errorcheck);

		if (error.get(1).isDisplayed()) {
			errorcheck = "true";
		}

		assertEquals("true", errorcheck);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() throws InterruptedException {

		final List<WebElement> text = getElementsByListClassName("form-control");
		text.get(2).clear();
		text.get(2).sendKeys("5");

		text.get(3).clear();
		text.get(3).sendKeys("A".repeat(2001));

		text.get(4).clear();
		text.get(4).sendKeys("A".repeat(2001));

		Thread.sleep(5);
		scrollBy("400");

		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);
		final WebElement submit = getElementBycssSelector("button[type='submit']");
		submit.click();

		Thread.sleep(5);
		scrollBy("400");

		visibilityTimeout(By.className("errorInput"), 5);
		final List<WebElement> error = getElementsByListClassName("errorInput");

		String feelerrorcheck = "";
		String weekerrorcheck = "";

		if (error.get(0).isDisplayed()) {
			feelerrorcheck = "true";
		}

		assertEquals("true", feelerrorcheck);

		if (error.get(1).isDisplayed()) {
			weekerrorcheck = "true";
		}

		assertEquals("true", weekerrorcheck);

		getEvidence(new Object() {
		});
	}

}
