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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		final List<WebElement> detail = getElementsBycssSelector("input[value='詳細']");
		detail.get(1).click();

		getEvidence(new Object() {
		});

		assertEquals("セクション詳細 | LMS", getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		final List<WebElement> weeklyreport = getElementsBycssSelector("input[type='submit']");
		scrollBy("100");
		weeklyreport.get(2).click();

		getEvidence(new Object() {
		});

		pageLoadTimeout(5);
		assertEquals("レポート登録 | LMS", getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		scrollBy("350");
		final WebElement text = getElementById("content_1");
		text.clear();
		text.sendKeys("かきくけこ");

		final WebElement button = getElementByclassName("btn-primary");
		button.click();

		getEvidence(new Object() {
		});

		pageLoadTimeout(5);
		assertEquals("セクション詳細 | LMS", getTitle());

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		final WebElement user = getElementText("ようこそ受講生ＡＡ２さん");
		user.click();

		getEvidence(new Object() {
		});

		assertEquals("ユーザー詳細", getTitle());

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() throws InterruptedException {

		scrollBy("300");

		final List<WebElement> detail = getElementsBycssSelector("input[value='詳細']");
		detail.get(0).click();

		visibilityTimeout(By.tagName("td"), 5);
		final List<WebElement> td = getElementsByTagName("td");
		assertEquals("かきくけこ", td.get(4).getText());

		getEvidence(new Object() {
		});

	}

}
