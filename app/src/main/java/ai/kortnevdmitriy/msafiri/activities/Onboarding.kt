package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import android.content.Intent
import android.os.Bundle
import com.codemybrainsout.onboarder.AhoyOnboarderActivity
import com.codemybrainsout.onboarder.AhoyOnboarderCard
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import java.util.*

class Onboarding : AhoyOnboarderActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Fabric.with(this, Crashlytics())
		
		/* Activity lacks a setContentView() method because of using the AhoyOnboarderActivity
        * This is achieved by creating AhoyOnboarderCard and adding them to pages */
		val ahoyOnboarderCard1 = AhoyOnboarderCard(
			"Buy Tickets",
			"The easiest way to buy bus tickets. Reliable and cost effective ",
			R.drawable.ic_bus_ticket
		)
		ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent)
		ahoyOnboarderCard1.setTitleColor(R.color.white)
		ahoyOnboarderCard1.setDescriptionColor(R.color.grey_200)
		ahoyOnboarderCard1.setTitleTextSize(dpToPixels(10, this))
		ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(8, this))
		ahoyOnboarderCard1.setIconLayoutParams(300, 300, 130, 8, 8, 8)
		
		val ahoyOnboarderCard2 = AhoyOnboarderCard(
			"Book Trips",
			"Book all your tickets for bus trips",
			R.drawable.ic_trip
		)
		ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent)
		ahoyOnboarderCard2.setTitleColor(R.color.white)
		ahoyOnboarderCard2.setDescriptionColor(R.color.grey_200)
		ahoyOnboarderCard2.setTitleTextSize(dpToPixels(10, this))
		ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(8, this))
		ahoyOnboarderCard2.setIconLayoutParams(300, 300, 130, 8, 8, 8)
		
		val ahoyOnboarderCard3 = AhoyOnboarderCard(
			"Manage Account",
			"Save your details to save time while accessing your tickets right here",
			R.drawable.ic_save_time
		)
		ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent)
		ahoyOnboarderCard3.setTitleColor(R.color.white)
		ahoyOnboarderCard3.setDescriptionColor(R.color.grey_200)
		ahoyOnboarderCard3.setTitleTextSize(dpToPixels(10, this))
		ahoyOnboarderCard3.setDescriptionTextSize(dpToPixels(8, this))
		ahoyOnboarderCard3.setIconLayoutParams(300, 300, 130, 8, 8, 8)
		
		val pages = ArrayList<AhoyOnboarderCard>()
		pages.add(ahoyOnboarderCard1)
		pages.add(ahoyOnboarderCard2)
		pages.add(ahoyOnboarderCard3)
		
		
		setOnboardPages(pages) // replacement for the setContentView()
		showNavigationControls(false) //Show/Hide navigation controls
		setColorBackground(R.color.colorPrimary) // sets the background color to the onboarding screens
		setFinishButtonTitle("Get Started")   //Set finish button text
	}
	
	override fun onFinishButtonPressed() {
		startActivity(Intent(applicationContext, Signin::class.java))
	}
}
