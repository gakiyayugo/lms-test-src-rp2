package jp.co.sss.lms.ct.f02_faq;

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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		final WebElement dropdown = getElementByclassName("dropdown-toggle");
		dropdown.click();

		final WebElement text = getElementText("ヘルプ");
		text.click();

		assertEquals("ヘルプ | LMS", getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		final WebElement text = getElementText("よくある質問");
		text.click();

		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);
		assertEquals("よくある質問 | LMS", getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//		
		final WebElement keyword = getElementByclassName("form-control");
		keyword.clear();
		keyword.sendKeys("キャンセル");
		keyword.sendKeys(Keys.ENTER);

		pageLoadTimeout(5);
		final List<WebElement> number = getElementsByListClassName("text-primary");
		int num = number.size();
		assertEquals(1, num);

		scrollBy("250");

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {

		final List<WebElement> clear = getElementsByListClassName("btn-primary");
		clear.get(1).click();

		final WebElement keyword = getElementByclassName("form-control");
		assertEquals("", keyword.getAttribute("value"));

		getEvidence(new Object() {
		});

	}

}
