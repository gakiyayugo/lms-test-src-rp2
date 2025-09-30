package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		final List<WebElement> detail = getElementsBycssSelector("input[value='詳細']");
		detail.get(1).click();

		getEvidence(new Object() {
		});

		assertEquals("セクション詳細 | LMS", getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {

		final WebElement detail = getElementBycssSelector("input[value='詳細']");
		detail.click();

		getEvidence(new Object() {
		});

		assertEquals("試験【ITリテラシー①】 | LMS", getTitle());

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {

		final WebElement start = getElementBycssSelector("input[value='試験を開始する']");
		start.click();

		getEvidence(new Object() {
		});

		assertEquals("ITリテラシー① | LMS", getTitle());

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() throws InterruptedException {

		scrollTo("document.body.scrollHeight");

		final WebElement advance = getElementBycssSelector("input[value='確認画面へ進む']");
		advance.click();

		visibilityTimeout(By.className("text-warning"), 5);
		final WebElement warning = getElementByclassName("text-warning");

		assertTrue(warning.isDisplayed());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {

		scrollTo("document.body.scrollHeight");

		final WebElement send = getElementById("sendButton");
		send.click();

		Thread.sleep(5);

		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		final List<WebElement> score = getElementsByTagName("small");

		assertEquals("あなたのスコア：0.0点   正答数：0／12", score.get(1).getText());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() throws InterruptedException {

		scrollTo("document.body.scrollHeight");

		visibilityTimeout(By.cssSelector("input[value='戻る']"), 5);
		final WebElement back = getElementBycssSelector("input[value='戻る']");
		back.click();

		visibilityTimeout(By.cssSelector("input[value='詳細']"), 5);
		final List<WebElement> detail = getElementsBycssSelector("input[value='詳細']");

		assertEquals(1, detail.size());

		getEvidence(new Object() {
		});

	}

}
